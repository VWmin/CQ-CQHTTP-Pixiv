package vwmin.coolq.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class GroupMessage extends BaseMessage implements HasId{
    @JsonProperty("group_id")
    private Long groupId;

    @JsonProperty("sub_type")
    private String subType;


    private Anonymous anonymous;

    @Override
    public Long getId() {
        return groupId;
    }

    @Data
    private class Anonymous{
        Long id;
        String name;
        String flag;
    }

}
