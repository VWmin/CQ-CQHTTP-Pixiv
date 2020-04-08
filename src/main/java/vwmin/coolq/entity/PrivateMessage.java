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
public class PrivateMessage extends BaseMessage implements HasId{
    @JsonProperty("sub_type")
    private String subType;

    @Override
    public Long getId() {
        return getUserId();
    }
}
