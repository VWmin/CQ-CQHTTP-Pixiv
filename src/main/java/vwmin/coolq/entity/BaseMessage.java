package vwmin.coolq.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
public class BaseMessage {
    private String post_type;
    private String message_type;

    private Integer message_id;
    private Long user_id;

    private String message;
    private String raw_message;

    private Integer font;
    private Sender sender;


    private String[] args;
    public String[] getArgs(){
        if(args == null) args = raw_message.split("\\s+");
        return args;
    }
}
