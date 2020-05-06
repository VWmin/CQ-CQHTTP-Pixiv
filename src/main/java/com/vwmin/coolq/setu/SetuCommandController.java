package com.vwmin.coolq.setu;

import com.vwmin.coolq.common.RedisUtil;
import com.vwmin.coolq.common.Utils;
import com.vwmin.terminalservice.*;
import com.vwmin.terminalservice.entity.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 16:16
 */
@CommandController(bind="setu")
public class SetuCommandController implements Reply {

    @CommandLine.Command
    SetuCommand command;

    private final
    Statistician statistician;

    public SetuCommandController(Statistician statistician) {
        this.statistician = statistician;
    }

    @Override
    public ReplyEntity call(PostEntity postEntity) {
        try{
            statistician.record(postEntity.getSender().getNickname());
            SetuEntity setu = command.call();
            return new ReplyEntity(new SetuConsumer(setu).getOne());
        }catch (IOException e){
            e.printStackTrace();
            return new ReplyEntity(Utils.handleException(e));
        }
    }

//    @SneakyThrows
//    @Override
//    public void call(CQClientApi cqClientApi, PostEntity postEntity) {
////        statistician.record(postEntity.getSender().getNickname());
//        SetuEntity setu = command.call();
//        List<MessageSegment> one = new SetuConsumer(setu).getOne();
//        RetEntity ret = SendBack(cqClientApi, postEntity, one);
//        RedisUtil.put(AutoDeleteMsg.CACHE, ret.getData().getMessage_id()+"", ret.getData().getMessage_id(), 30);
//    }

//    private RetEntity SendBack(CQClientApi cqClientApi, PostEntity postEntity, List<MessageSegment> one) {
//        String message_type = postEntity.getMessage_type();
//        switch (message_type){
//            case "private":
//                SendEntity.PrivateSendEntity privateSendEntity =
//                        new SendEntity.PrivateSendEntity(postEntity.getUser_id(), one);
//                return cqClientApi.sendPrivateMsg(privateSendEntity);
//            case "group":
//                List<MessageSegment> build = new MessageSegmentBuilder().at(postEntity.getUser_id()).build();
//                build.addAll(one);
//                SendEntity.GroupSendEntity groupSendEntity =
//                        new SendEntity.GroupSendEntity(postEntity.getGroup_id(), build);
//                return cqClientApi.sendGroupMsg(groupSendEntity);
//            default:
//                SendEntity.DiscussSendEntity discussSendEntity =
//                        new SendEntity.DiscussSendEntity(postEntity.getGroup_id(), one);
//                return cqClientApi.sendDiscussMsg(discussSendEntity);
//        }
//    }
}
