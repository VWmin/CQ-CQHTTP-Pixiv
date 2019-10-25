package vwmin.coolq.entity;

import lombok.Data;

import java.util.List;

@Data
public class SendMessageEntity {
    private String message_type;
    private Long user_id;
    private Long group_id;
    private Long discuss_id;

    private List<MessageSegment> message;

    private Boolean auto_escape;

}
