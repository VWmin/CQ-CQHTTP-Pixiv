package vwmin.coolq.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GroupMessage extends BaseMessage implements HasId{
    private Long group_id;

    private String sub_type;
    private Anonymous anonymous;

    @Override
    public Long getId() {
        return group_id;
    }

    @Data
    private class Anonymous{
        Long id;
        String name;
        String flag;
    }

}
