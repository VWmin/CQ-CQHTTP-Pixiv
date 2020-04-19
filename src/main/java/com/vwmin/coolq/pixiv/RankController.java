package com.vwmin.coolq.pixiv;

import com.vwmin.coolq.common.Utils;
import com.vwmin.terminalservice.CommandController;
import com.vwmin.terminalservice.Reply;
import com.vwmin.terminalservice.entity.MessageSegment;
import com.vwmin.terminalservice.entity.PostEntity;
import com.vwmin.terminalservice.entity.ReplyEntity;
import picocli.CommandLine;

import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 17:56
 */
@CommandController(bind="rank")
public class RankController implements Reply {

    @CommandLine.Command
    RankCommand command;

    @Override
    public ReplyEntity call(PostEntity postEntity) {
        try{
            Illusts call = command.call();
            List<MessageSegment> one = new IllustsConsumer(call, postEntity.getUser_id()).top10();
            return new ReplyEntity(one);
        }catch (Exception e){
            e.printStackTrace();
            return new ReplyEntity(Utils.handleException(e));
        }
    }
}
