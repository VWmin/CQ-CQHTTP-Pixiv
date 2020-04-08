package vwmin.coolq.core;

import vwmin.coolq.core.BaseConsumer;
import vwmin.coolq.core.MessageSegmentBuilder;
import vwmin.coolq.entity.MessageSegment;

import java.util.List;

public class ErrorConsumer implements BaseConsumer {

    public static List<MessageSegment> response(Long userId, String cause){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        builder.addAtSegment(userId);
        builder.addTextSegment(cause);
        return builder.build();
    }
}
