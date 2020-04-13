package com.vwmin.coolq.pixiv;

import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.MessageSegment;

import java.io.IOException;
import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 18:10
 */
public class IllustConsumer {

    private Illust illust;

    /**发出指令的用户的id*/
    private Long userId;

    public IllustConsumer(Illust illust, Long userId){
        this.illust = illust;
        this.userId = userId;
    }

    public List<MessageSegment> result() throws IOException {
        MessageSegmentBuilder builder = new MessageSegmentBuilder();

        if(illust == null || illust.getIllust() == null){
            builder.plainText("请检查id是否有误");
            return builder.build();
        }

        builder.image(illust.getIllust().getId()+IllustUtils.getImgType(illust.getIllust()),
                IllustUtils.getMetaSinglePage(illust.getIllust()));

        return builder.build();

    }
}

