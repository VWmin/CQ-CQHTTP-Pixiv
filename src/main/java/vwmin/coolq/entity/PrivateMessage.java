package vwmin.coolq.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class PrivateMessage extends BaseMessage implements HasId{
    private String sub_type;

    @Override
    public Long getId() {
        return getUser_id();
    }
}
