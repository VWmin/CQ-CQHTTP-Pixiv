package vwmin.coolq.function.pixiv.util;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.io.IOException;
import java.util.List;

import static vwmin.coolq.function.pixiv.util.IllustUtil.getImgType;
import static vwmin.coolq.function.pixiv.util.IllustUtil.getMetaSinglePage;

public class IllustResponseConsumer {

    private IllustResponse illust;

    /**发出指令的用户的id*/
    private Long userId;

    public IllustResponseConsumer(IllustResponse illust, Long userId){
        this.illust = illust;
        this.userId = userId;
    }

    public List<MessageSegment> result() throws IOException {
        MessageSegmentBuilder builder = new MessageSegmentBuilder();

        if(illust == null || illust.getIllust() == null){
            builder.addAtSegment(userId)
                    .addTextSegment(" 好像没有这个id嗷");
            return builder.build();
        }

        builder.addAtSegment(userId)
                .addImageSegment(illust.getIllust().getId()+getImgType(illust.getIllust()), getMetaSinglePage(illust.getIllust()));

        return builder.build();

    }
}
