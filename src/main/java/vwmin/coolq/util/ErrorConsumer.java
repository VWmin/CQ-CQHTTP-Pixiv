package vwmin.coolq.util;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;

import java.util.List;

public class ErrorConsumer implements BaseConsumer {

    private Long user_id;
    private String cause;


    public ErrorConsumer(Long user_id, String cause){
        this.user_id = user_id;
        this.cause = cause;
    }

    public List<MessageSegment> response(){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        builder.addAtSegment(user_id);
        builder.addTextSegment(cause);
        return builder.build();
    }


}
