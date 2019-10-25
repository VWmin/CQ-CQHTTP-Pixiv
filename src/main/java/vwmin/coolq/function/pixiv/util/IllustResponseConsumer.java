package vwmin.coolq.function.pixiv.util;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.util.List;

import static vwmin.coolq.function.pixiv.util.IllustUtil.getImgType;
import static vwmin.coolq.function.pixiv.util.IllustUtil.getMetaSinglePage;

public class IllustResponseConsumer {
    private IllustResponse illust;
    private Long user_id; //发出指令的用户的id

    public IllustResponseConsumer(IllustResponse illust, Long user_id){
        this.illust = illust;
        this.user_id = user_id;
    }

    public List<MessageSegment> result(){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();

        if(illust == null || illust.getIllust() == null){
            builder.addAtSegment(user_id)
                    .addTextSegment(" 好像没有这个id嗷");
            return builder.build();
        }

        builder.addAtSegment(user_id)
                .addImageSegment(illust.getIllust().getId()+getImgType(illust.getIllust()), getMetaSinglePage(illust.getIllust()));

        return builder.build();

    }
}
