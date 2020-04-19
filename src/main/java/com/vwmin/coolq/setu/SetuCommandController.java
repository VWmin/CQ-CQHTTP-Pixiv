package com.vwmin.coolq.setu;

import com.vwmin.coolq.common.Utils;
import com.vwmin.terminalservice.CommandController;
import com.vwmin.terminalservice.Reply;
import com.vwmin.terminalservice.entity.MessageSegment;
import com.vwmin.terminalservice.entity.PostEntity;
import com.vwmin.terminalservice.entity.ReplyEntity;
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

    @Override
    public ReplyEntity call(PostEntity postEntity) {
        try{
            SetuEntity setu = command.call();
            List<MessageSegment> one = new SetuConsumer(setu, postEntity.getUser_id()).getOne();
            return new ReplyEntity(one);
        }catch (IOException e){
            e.printStackTrace();
            return new ReplyEntity(Utils.handleException(e));
        }
    }
}
