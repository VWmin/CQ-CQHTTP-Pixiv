package vwmin.coolq.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import vwmin.coolq.entity.*;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.enums.MessageType;
import vwmin.coolq.function.pixiv.service.ScheduleTask;
import vwmin.coolq.function.setu.SetuSession;
import vwmin.coolq.service.ArgsDispatcher;
import vwmin.coolq.session.BaseSession;
import vwmin.coolq.function.pixiv.RankSession;
import vwmin.coolq.function.saucenao.SearchSession;
import vwmin.coolq.function.pixiv.WordSession;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RestController
public class PostController {
    private static final Gson GSON = new Gson();
    private static final Pattern REGEX_MESSAGE_TYPE = Pattern.compile("\"message_type\":\".*?\"");
    private static final Pattern REGEX_POST_TYPE = Pattern.compile("\"post_type\":\".*?\"");

    private final
    Map<String, ArgsDispatcher> argsDispatcherMap;

    private final ScheduleTask scheduleTask;

    private static
    Map<Long, BaseSession> sessionMap = new ConcurrentHashMap<>();


    public PostController(Map<String, ArgsDispatcher> argsDispatcherMap,
                          ScheduleTask scheduleTask) {
        this.argsDispatcherMap = argsDispatcherMap;
        this.scheduleTask = scheduleTask;
    }


    @PostMapping("")
    public QuickReply printPostContent(HttpServletRequest request){
        // 排除非CQ客户端发来的请求
        if(!request.getHeader("user-agent").startsWith("CQHttp")){
            return null;
        }

        // 排除错误的请求体
        String requestBody =  getRequestBody(request);
        if("jsonError".equals(requestBody)){
            return null;
        }

        // 排除非message类型的上报
        String postType = getPostType(requestBody);
        if(!"message".equals(postType)){
            return null;
        }

        // 包装收到的消息
        BaseMessage message = processMessage(requestBody);
        Assert.notNull(message, "message解析错误，或者message_type=discuss.");
        message.setArgs(arg0Preprocess(message.getArgs()));
        log.info("收到上报数据 >> "+message);


        for (Long key : sessionMap.keySet()){
            if(sessionMap.get(key).isClosed()){
                sessionMap.remove(key);
            }
        }

        final Long userId = message.getUser_id();
        final String arg0 = message.getArgs()[0];
        final String[] args = message.getArgs();
        if(sessionMap.get(userId) != null){
            //存在会话
            BaseSession session = sessionMap.get(userId);
            if(getBelong(arg0) == session.getBelong()){
                //会话存在时，新命令属于原会话类型
                session.update(((HasId)message).getId(), message.getMessage_type(), message.getArgs());
                argsDispatcherMap.get(session.getBelong().getKey()).createSender(session, args).send();
            }else{
                //会话存在时，新命令不属于原会话类型
                session = creatSession(message);
                sessionMap.replace(userId, session);
                argsDispatcherMap.get(getBelong(arg0).getKey()).createSender(session, args).send();
            }

        }else{
            //如果map中没有找到id-->session
            BaseSession session = creatSession(message);
            if(session == null) {
                return null;
            }
            sessionMap.put(userId, session);
            argsDispatcherMap.get(getBelong(arg0).getKey()).createSender(session, args).send();

        }

        return null;
    }


    private String[] arg0Preprocess(String[] args) {
        if (args[0].contains("一张色图")){
            args[0] = "setu";
        } else if (args[0].contains("search")){
            String arg0 = args[0];
            args = new String[]{arg0.substring(0, 6), arg0.substring(6)};
        }
        return args;
    }

    /**新创建的命令需要在这里与分发器进行关联*/
    private ArgsDispatcherType getBelong(String arg0) {
        String[] pixivArgs = {"rank", "word", "next"};
        String[] saucenaoArgs = {"search"};
        String[] setuArgs = {"setu"};

        for(String per : pixivArgs){
            if(per.equals(arg0)){
                return ArgsDispatcherType.PIXIV;
            }
        }
        for(String per : saucenaoArgs){
            if(per.equals(arg0)){
                return ArgsDispatcherType.SAUCENAO;
            }
        }
        for (String per :setuArgs){
            if (per.equals(arg0)){
                return ArgsDispatcherType.SETU;
            }
        }
        return ArgsDispatcherType.NULL;
    }

    /**新创建的session需要才这里与命令进行关联*/
    private BaseSession creatSession(BaseMessage message) {
        Long userId = message.getUser_id();
        Long sourceId = ((HasId)message).getId();
        MessageType messageType = MessageType.valueOf(message.getMessage_type().toUpperCase());

        switch (message.getArgs()[0]){
            case "rank":
                return new RankSession(userId, sourceId, messageType);
            case "word":
                return new WordSession(userId, sourceId, messageType);
            case "search":
                return new SearchSession(userId, sourceId, messageType);
            case "setu":
                return new SetuSession(userId, sourceId, messageType);
            default:
                break;
        }

        return null;
    }

    @GetMapping("")
    public String test(){
        return "Hello ?";
    }

//    @GetMapping("/update/rank")
//    public String updateRank(){
//        scheduleTask.download();
//        return "我试试？";
//    }


    private String getRequestBody(HttpServletRequest request){
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = null;
        String requestBody = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            requestBody = URLDecoder.decode(stringBuilder.toString(), "UTF-8");
            requestBody = requestBody.substring(requestBody.indexOf("{"));
            bufferedReader.close();
            return requestBody;
        } catch (IOException e) {

            e.printStackTrace();
            return "jsonError";
        }
    }

    private String getPostType(String requestBody){
        String postType;

        Matcher m = REGEX_POST_TYPE.matcher(requestBody);
        if(m.find()){
            postType = m.group().split(":")[1].replace("\"", "");
            return postType;
        }else{
            return null;
        }
    }

    private BaseMessage processMessage(String requestBody){
        String messageType = getMessageType(requestBody);
        Assert.notNull(messageType, "messageType could not be null.");

        BaseMessage message = null;

        switch (messageType){
            case "private":
                message = processPrivateMessage(requestBody);
                break;
            case "group":
                message = processGroupMessage(requestBody);
                break;
            case "discuss":
                // TODO: 2019/8/5 抛一个异常？反正讨论组不管
                break;
            default:
                break;
        }
        return message;
    }

    private GroupMessage processGroupMessage(String requestBody) {
        return GSON.fromJson(requestBody, GroupMessage.class);
    }

    private PrivateMessage processPrivateMessage(String requestBody) {
        return GSON.fromJson(requestBody, PrivateMessage.class);
    }

    private String getMessageType(String requestBody){
        String messageType;
        Matcher m = REGEX_MESSAGE_TYPE.matcher(requestBody);
        if(m.find()){
            messageType = m.group().split(":")[1].replace("\"", "");
            return messageType;
        }else{
            return null;
        }
    }


}
