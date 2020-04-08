package vwmin.coolq.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MessageSegment {
    private String type;
    private Map<String, Object> data;

    public MessageSegment(){
        this.data = new HashMap<>();
    }

    public void addData(String key, Object val){
        data.put(key, val);
    }

}
