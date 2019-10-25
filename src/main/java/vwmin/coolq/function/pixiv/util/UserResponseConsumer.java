package vwmin.coolq.function.pixiv.util;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.util.List;

import static vwmin.coolq.function.pixiv.util.IllustUtil.getImgType;
import static vwmin.coolq.function.pixiv.util.IllustUtil.getMetaSinglePage;

public class UserResponseConsumer {
    private UserResponse userResponse;
    private Long user_id;

    public UserResponseConsumer(UserResponse response, Long user_id){
        this.user_id= user_id;
        this.userResponse = response;
    }

    public List<MessageSegment> result(){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();

        if(userResponse == null || userResponse.getUser() == null){
            builder.addAtSegment(user_id)
                    .addTextSegment(" 好像没有这个id嗷");
            return builder.build();
        }

        builder.addAtSegment(user_id)
                .addTextSegment(" 你要找的可能是："+userResponse.getUser().getName());

        return builder.build();

    }


}
