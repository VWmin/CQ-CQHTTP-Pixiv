package com.vwmin.coolq.saucenao;

import com.vwmin.coolq.common.Utils;
import com.vwmin.coolq.pixiv.PixivApi;
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
 * @date 2020/4/13 10:19
 */
@CommandController(bind="search")
public class SaucenaoCommandController implements Reply {

    @CommandLine.Command
    SaucenaoCommand command;

    private final
    PixivApi pixivApi;

    public SaucenaoCommandController(PixivApi pixivApi){
        this.pixivApi = pixivApi;
    }

    @Override
    public ReplyEntity call(PostEntity postEntity) {
        try{
            SaucenaoEntity call = command.call();
            List<MessageSegment> one = new SaucenaoConsumer(call, pixivApi).mostly();
            return new ReplyEntity(one);
        }catch (Exception e){
            e.printStackTrace();
            return new ReplyEntity(Utils.handleException(e));
        }
    }
}
