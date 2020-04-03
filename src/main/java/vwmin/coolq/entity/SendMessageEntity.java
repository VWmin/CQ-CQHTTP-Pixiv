package vwmin.coolq.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vwmin.coolq.enums.MessageType;

import java.util.List;

@Data
public class SendMessageEntity {
    @SerializedName("message_type")
    protected String messageType;

    protected List<MessageSegment> message;

    @SerializedName("auto_escape")
    protected Boolean autoEscape;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class DiscussSendMessageEntity extends SendMessageEntity{
        @SerializedName("discuss_id")
        private Long discussId;

        DiscussSendMessageEntity(String messageType, Long sourceId, List<MessageSegment> message) {
            this.messageType = messageType;
            this.message = message;
            this.discussId = sourceId;
        }

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class GroupSendMessageEntity extends SendMessageEntity {
        @SerializedName("group_id")
        private Long groupId;

        GroupSendMessageEntity(String messageType, Long sourceId, List<MessageSegment> message) {
            this.messageType = messageType;
            this.message = message;
            this.groupId = sourceId;
        }
    }


    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class PrivateSendMessageEntity extends SendMessageEntity{
        @SerializedName("user_id")
        private Long userId;

        PrivateSendMessageEntity(String messageType, Long sourceId, List<MessageSegment> message) {
            this.messageType = messageType;
            this.message = message;
            this.userId = sourceId;
        }
    }

    public static SendMessageEntity create(MessageType messageType, Long sourceId, List<MessageSegment> message){
        switch (messageType){
            case PRIVATE:
                return new PrivateSendMessageEntity(messageType.type(), sourceId, message);
            case GROUP:
                return new GroupSendMessageEntity(messageType.type(), sourceId, message);
            case DISCUSS:
                return new DiscussSendMessageEntity(messageType.type(), sourceId, message);
            default:
                return null;
        }
    }

}
