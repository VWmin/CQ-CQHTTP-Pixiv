package vwmin.coolq.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 消息发送人
 */
@Data
@ToString
public class Sender {
    String nickname;
    String sex;
    Integer age;
}
