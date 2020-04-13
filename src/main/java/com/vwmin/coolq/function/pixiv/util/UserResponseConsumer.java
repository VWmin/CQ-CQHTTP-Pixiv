package com.vwmin.coolq.function.pixiv.util;

//import vwmin.coolq.entity.MessageSegment;
//import vwmin.coolq.function.pixiv.entity.UserResponse;
//import vwmin.coolq.core.MessageSegmentBuilder;
//
//import java.util.List;
//
//public class UserResponseConsumer {
//    private UserResponse userResponse;
//    private Long userId;
//
//    public UserResponseConsumer(UserResponse response, Long userId){
//        this.userId = userId;
//        this.userResponse = response;
//    }
//
//    public List<MessageSegment> result(){
//        MessageSegmentBuilder builder = new MessageSegmentBuilder();
//
//        if(userResponse == null || userResponse.getUser() == null){
//            builder.addAtSegment(userId)
//                    .addTextSegment(" 好像没有这个id嗷");
//            return builder.build();
//        }
//
//        builder.addAtSegment(userId)
//                .addTextSegment(" 你要找的可能是："+userResponse.getUser().getName());
//
//        return builder.build();
//
//    }
//
//
//}
