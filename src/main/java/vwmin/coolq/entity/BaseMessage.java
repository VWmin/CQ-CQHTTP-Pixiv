package vwmin.coolq.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseMessage {
    @JsonProperty("post_type")
    private String postType;

    @JsonProperty("message_type")
    private String messageType;

    @JsonProperty("message_id")
    private Integer messageId;

    @JsonProperty("user_id")
    private Long userId;

    private String message;

    @JsonProperty("raw_message")
    private String rawMessage;

    private Integer font;
    private Sender sender;


    private String[] args;
    public String[] getArgs(){
        if(args == null) {
            args = message.split("\\s+");
        }
        return args;
    }
}
