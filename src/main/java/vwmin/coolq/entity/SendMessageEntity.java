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

    public SendMessageEntity(String message_type, Long source_id, List<MessageSegment> message) {
        this.message_type = message_type;
        this.message = message;
        switch (message_type){
            case "private":
                user_id = source_id;
                break;
            case "group":
                group_id = source_id;
                break;
            case "discuss":
                discuss_id = source_id;
                break;
        }
    }

}
