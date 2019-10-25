package vwmin.coolq.function.pixiv.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vwmin.coolq.entity.*;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.service.ArgsDispatcher;
import vwmin.coolq.service.MessageSender;
import vwmin.coolq.service.CQClientService;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.util.*;


@Component("pixivArgsDispatcher")
@Slf4j
public class PixivArgsDispatcher implements ArgsDispatcher {
    private final CQClientService cqClientService;
    private final PixivService pixivService;

    public PixivArgsDispatcher(PixivService pixivService, CQClientService cqClientService){
        this.pixivService = pixivService;
        this.cqClientService = cqClientService;
    }

    @Override
    public MessageSender setPostMessage(BaseMessage postMessage) {
        String[] args = postMessage.getArgs();

        if(args[0].equals("rank")){
            return new PixivSender(postMessage, "rank", args);
        }else if(args[0].equals("detail")){
            return new PixivSender(postMessage, "detail", args);
        }else if(args[0].equals("user")){
            return new PixivSender(postMessage, "user", args);
        }else{
            return new PixivSender(postMessage, true);
        }
    }


    private class PixivSender implements MessageSender{
        private final String[] RANK_MODE_LIST = {"day", "week", "month", "day_male", "day_female", "week_original",
                                            "week_rookie", "day_manga", "day_r18", "day_male_r18", "day_female_r18",
                                            "week_r18", "week_r18g"};
        private BaseMessage postMessage;
        private boolean isError = false;

        private String type;
        private String[] args;

        private String errorMsg = "未定义错误";


        PixivSender(BaseMessage postMessage, boolean isError){
            this.postMessage = postMessage;
            this.isError = isError;
        }

        PixivSender(BaseMessage postMessage, boolean isError, String errorMsg){
            this.postMessage = postMessage;
            this.isError = isError;
            this.errorMsg = errorMsg;
        }

        PixivSender(BaseMessage postMessage, String type, String[] args){
            this.postMessage = postMessage;
            this.type = type;
            this.args = args;
            this.isError = checkArgs();
        }

        //有误返回真
        private boolean checkArgs() {
            // TODO: 2019/10/24 很蠢 得改
            boolean flag = false;
            if(args.length == 1){
                flag = true;
                if(type.equals("rank"))
                    errorMsg = "可选参数有：【day, week, month, day_male, day_female, week_original, week_rookie, day_manga, day_r18, day_male_r18, day_female_r18, week_r18, week_r18g】";
                else if(type.equals("detail"))
                    errorMsg = "等等 我还没想好要说啥";
                else if(type.equals("user"))
                    errorMsg = "等等 我还没想好";
            }else if(type.equals("rank") && checkRankMode(args[1])) {
                flag = true;
                errorMsg = "参数有误，可选参数有：【day, week, month, day_male, day_female, week_original, week_rookie, day_manga, day_r18, day_male_r18, day_female_r18, week_r18, week_r18g】";
            }else if(type.equals("detail") && !args[1].matches("^\\d+$")){
                flag = true;
                errorMsg = "参数有误，试试detail 插画id";
            }else if(type.equals("user") && !args[1].matches("^\\d+$")){
                flag = true;
                errorMsg = "参数有误，试试user 用户id";
            }
            return flag;
        }

        //有误返回真
        private boolean checkRankMode(String mode){
            boolean flag = true;
            for(String e : RANK_MODE_LIST){
                if(mode.equals(e)) flag = false;
            }
            return flag;
        }

        @Override
        public void send() {
            if(isError){
                onError();
            }else{
                onSuccess();
            }
        }

        //指命令错误
        private void onError(){
            SendMessageEntity send = cqClientService.creatMessageEntity(postMessage.getMessage_type(), ((HasId) postMessage).getId(),
                            new MessageSegmentBuilder().addTextSegment(errorMsg).build());

            cqClientService.sendMessage(send);
        }

        // 指命令正确
        private void onSuccess(){
            SendMessageEntity send = null;

            if(type.equals("rank")){
                String mode = args[1];
                String date = null;
                if(args.length == 3) date = args[2];

                ListIllustResponse rankResponse = pixivService.getRank(mode, date);
                RankResponseConsumer consumer = new RankResponseConsumer(mode, rankResponse);
                send = cqClientService.creatMessageEntity(postMessage.getMessage_type(), ((HasId) postMessage).getId(), consumer.top10());
            }else if(type.equals("detail")){
                Integer illust_id = Integer.parseInt(args[1]);

                IllustResponse illustResponse = pixivService.getIllustById(illust_id);
                IllustResponseConsumer consumer = new IllustResponseConsumer(illustResponse, postMessage.getUser_id());
                send = cqClientService.creatMessageEntity(postMessage.getMessage_type(), ((HasId) postMessage).getId(), consumer.result());
            }else if(type.equals("user")){
                Integer user_id = Integer.parseInt(args[1]);

                UserResponse userResponse = pixivService.getUserById(user_id);
                UserResponseConsumer consumer = new UserResponseConsumer(userResponse, postMessage.getUser_id());
                send = cqClientService.creatMessageEntity(postMessage.getMessage_type(), ((HasId) postMessage).getId(), consumer.result());
            }
            cqClientService.sendMessage(send);

        }

    }
}