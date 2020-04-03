package vwmin.coolq.enums;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/3 13:49
 */
public enum  MessageType {
    /***/
    PRIVATE("private"),
    GROUP("group"),
    DISCUSS("discuss");

    private String type;

    MessageType(String type){
        this.type = type;
    }

    public String type(){
        return type;
    }

}
