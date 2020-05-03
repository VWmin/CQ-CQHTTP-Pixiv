package com.vwmin.coolq.pixiv.subscribe;

import com.vwmin.coolq.common.RedisUtil;
import com.vwmin.terminalservice.CommandController;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.Reply;
import com.vwmin.terminalservice.entity.PostEntity;
import com.vwmin.terminalservice.entity.ReplyEntity;

import static com.vwmin.coolq.pixiv.subscribe.AutoDetectNewWorks.SUBSCRIBERS;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/5/3 16:26
 */
@CommandController(bind = "!uns")
public class UnsubscribeCommandController implements Reply {
    @Override
    public ReplyEntity call(PostEntity postEntity) {
        Boolean remove = RedisUtil.remove(SUBSCRIBERS, postEntity.getUser_id() + "");
        return new ReplyEntity(
                new MessageSegmentBuilder()
                .plainText(remove?"取消订阅成功":"取消订阅失败，请稍后重试")
                .build()
        );
    }
}
