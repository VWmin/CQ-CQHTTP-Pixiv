package vwmin.coolq.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class SendMessageEntity {
    @SerializedName("message_type")
    private String messageType;
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("group_id")
    private Long groupId;
    @SerializedName("discuss_id")
    private Long discussId;

    private List<MessageSegment> message;

    @SerializedName("auto_escape")
    private Boolean autoEscape;

    public SendMessageEntity(String messageType, Long sourceId, List<MessageSegment> message) {
        this.messageType = messageType;
        this.message = message;
        switch (messageType){
            case "private":
                userId = sourceId;
                break;
            case "group":
                groupId = sourceId;
                break;
            case "discuss":
                discussId = sourceId;
                break;
            default:
                break;
        }
    }

}
