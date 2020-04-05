package vwmin.coolq.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vwmin.coolq.enums.MessageType;

import java.util.List;

@Data
public class SendMessageEntity {
    @JsonProperty("message_type")
    protected String messageType;

    protected List<MessageSegment> message;

    @JsonProperty("auto_escape")
    protected Boolean autoEscape;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class DiscussSendMessageEntity extends SendMessageEntity{
        @JsonProperty("discuss_id")
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
        @JsonProperty("group_id")
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
        @JsonProperty("user_id")
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
