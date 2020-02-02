package vwmin.coolq.controller;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import vwmin.coolq.entity.*;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.function.pixiv.service.ScheduleTask;
import vwmin.coolq.service.ArgsDispatcher;
import vwmin.coolq.session.BaseSession;
import vwmin.coolq.session.RankSession;
import vwmin.coolq.session.SearchSession;
import vwmin.coolq.session.WordSession;
import vwmin.coolq.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@RestController
public class PostController {
    private static final Gson gson = new Gson();

    private final
    Map<String, ArgsDispatcher> argsDispatcherMap;

    private final ScheduleTask scheduleTask;

    private static
    Map<Long, BaseSession> sessionMap = new HashMap<>();
//    Set<Long> saucenaoSession = new TreeSet<>();

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
        String post_type = getPostType(requestBody);
        if(!"message".equals(post_type)){
            return null;
        }

        // 包装收到的消息
        BaseMessage message = processMessage(requestBody);
        Assert.notNull(message, "message解析错误，或者message_type=discuss.");
        log.info("收到上报数据 >> "+message);

        //todo: 把这个整合进session里，现在先管上线
        if (message.getMessage().contains("一张色图")){
            argsDispatcherMap.get(ArgsDispatcherType.SETU.getKey()).setPostMessage(message).send();
            return null;
        }

        for (Long key : sessionMap.keySet()){
            if(sessionMap.get(key).isClosed()){
                sessionMap.remove(key);
            }
        }

        Long user_id = message.getUser_id();
        if(sessionMap.get(user_id) != null){
            BaseSession session = sessionMap.get(user_id);
            if(message.getArgs()[0].contains("CQ:image")){ //有任意会话存在时，同用户在任何地方发图片都会触发
                if(session.getBelong() == ArgsDispatcherType.SAUCENAO){ //那么让图片指令只指向saucenao
                    String url = StringUtil.matchUrl(message.getMessage());
                    session.update(((HasId)message).getId(), message.getMessage_type(), StringUtil.append(message.getArgs(), url));
                    argsDispatcherMap.get(session.getBelong().getKey()).setSession(session).send();
                }else{
                    return null;
                }
            }
            if(getBelong(message.getArgs()[0]) == session.getBelong()){ //会话存在时，新命令属于原会话类型
                session.update(((HasId)message).getId(), message.getMessage_type(), message.getArgs());
                argsDispatcherMap.get(session.getBelong().getKey()).setSession(session).send();
            }else{ //会话存在时，新命令不属于原会话类型
                sessionMap.remove(user_id);
                session = creatSession(message);
                if(session == null) return null;
                sessionMap.put(user_id, session);
                argsDispatcherMap.get(Objects.requireNonNull(getBelong(message.getArgs()[0])).getKey()).setSession(session).send();
            }

        }else{ //如果map中没有找到id-->session
            BaseSession session = creatSession(message);
            if(session == null) return null;
            sessionMap.put(user_id, session);
            argsDispatcherMap.get(Objects.requireNonNull(getBelong(message.getArgs()[0])).getKey()).setSession(session).send();

        }





//        if("rank".equals(message.getArgs()[0])){
//            RankSession rankSession = new RankSession(((HasId) message).getId(), message.getMessage_type());
//            argsDispatcherMap.get(ArgsDispatcherType.PIXIV.getKey()).setPostMessage(rankSession).send();
//        }else if("word".equals(message.getArgs()[0])){
//            argsDispatcherMap.get(ArgsDispatcherType.PIXIV.getKey()).setPostMessage(message).send();
//        }
//        else if("search".equals(message.getArgs()[0])){
//            // 收到来自用户的搜图请求，添加状态为等待
//            saucenaoSession.add(((HasId) message).getId());
//        }else if(message.getArgs()[0].contains("CQ:image") && saucenaoSession.contains(((HasId) message).getId())){
//            //处理搜图请求，去除状态
//            saucenaoSession.remove(((HasId) message).getId());
//            argsDispatcherMap.get(ArgsDispatcherType.SAUCENAO.getKey()).setPostMessage(message).send();
//        }else if("download".equals(message.getArgs()[0])){
//            argsDispatcherMap.get(ArgsDispatcherType.DOWNLOAD.getKey()).setPostMessage(message).send();
//        }else if("detail".equals(message.getArgs()[0])){
//            argsDispatcherMap.get(ArgsDispatcherType.PIXIV.getKey()).setPostMessage(message).send();
//        }else if("user".equals(message.getArgs()[0])){
//            argsDispatcherMap.get(ArgsDispatcherType.PIXIV.getKey()).setPostMessage(message).send();
//        }


        return null;
    }

    //新创建的命令需要在这里与分发器进行关联
    private ArgsDispatcherType getBelong(String arg0) {
        String[] pixivArgs = {"rank", "word", "next"};
        String[] saucenaoArgs = {"search"};
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
        return null;
    }

    //新创建的session需要才这里与命令进行关联
    private BaseSession creatSession(BaseMessage message) {
        Long user_id = message.getUser_id();
        Long source_id = ((HasId)message).getId();
        String message_type = message.getMessage_type();
        String[] args = message.getArgs();

        switch (message.getArgs()[0]){
            case "rank":
                return new RankSession(user_id, source_id, message_type, args);
            case "word":
                return new WordSession(user_id, source_id, message_type, args);
            case "search":
                return new SearchSession(user_id, source_id, message_type, args);
        }

        return null;
    }

    @GetMapping("")
    public String test(){
        return "Hello ?";
    }

    @GetMapping("/update/rank")
    public String updateRank(){
        scheduleTask.download();
        return "我试试？";
    }


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
//            request.setAttribute("inputParam", requestBody);
//            System.out.println("JsonReq reqBody>>>>>" + requestBody);
            bufferedReader.close();
            return requestBody;
        } catch (IOException e) {

            e.printStackTrace();
            return "jsonError";
        }
    }

    private String getPostType(String requestBody){
        String post_type;
        Pattern p = Pattern.compile("\"post_type\":\".*?\"");
        Matcher m = p.matcher(requestBody);
        if(m.find()){
            post_type = m.group().split(":")[1].replace("\"", "");
            return post_type;
        }else{
            return null;
        }
    }

    private BaseMessage processMessage(String requestBody){
        String message_type = getMessageType(requestBody);
        Assert.notNull(message_type, "message_type could not be null.");

        BaseMessage message = null;

        switch (message_type){
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
        return gson.fromJson(requestBody, GroupMessage.class);
    }

    private PrivateMessage processPrivateMessage(String requestBody) {
        return gson.fromJson(requestBody, PrivateMessage.class);
    }

    private String getMessageType(String requestBody){
        String message_type;
        Pattern p = Pattern.compile("\"message_type\":\".*?\"");
        Matcher m = p.matcher(requestBody);
        if(m.find()){
            message_type = m.group().split(":")[1].replace("\"", "");
            return message_type;
        }else{
            return null;
        }
    }


}
