package com.vwmin.coolq.pixiv.subscribe;

import com.vwmin.coolq.common.RedisUtil;
import com.vwmin.coolq.pixiv.PixivApi;
import com.vwmin.terminalservice.CommandController;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.Reply;
import com.vwmin.terminalservice.entity.PostEntity;
import com.vwmin.terminalservice.entity.ReplyEntity;
import picocli.CommandLine;

import static com.vwmin.coolq.pixiv.subscribe.AutoDetectNewWorks.*;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/5/3 16:02
 */
@CommandController(bind = "!subscribe")
public class SubscribeCommandController implements Reply {

    @CommandLine.Command
    private SubscribeCommand command;

    private final
    PixivApi api;

    public SubscribeCommandController(PixivApi api){
        this.api = api;
    }


    @Override
    public ReplyEntity call(PostEntity postEntity) {
        api.login(command.username, command.password);
        RedisUtil.put(SUBSCRIBERS, postEntity.getUser_id()+"", command.username);
        return new ReplyEntity(new MessageSegmentBuilder().plainText("订阅成功").build());
    }
}
