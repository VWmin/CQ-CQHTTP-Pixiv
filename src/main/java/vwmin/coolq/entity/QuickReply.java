package vwmin.coolq.entity;

import lombok.Data;

@Data
public class QuickReply {
    private String reply;


    public QuickReply(String reply){
        this.reply = reply;
    }
}
