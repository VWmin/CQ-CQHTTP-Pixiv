package com.vwmin.coolq.honkar3rd;

import com.vwmin.coolq.honkar3rd.entities.Item;
import com.vwmin.coolq.honkar3rd.entities.Person;
import com.vwmin.coolq.honkar3rd.gacha.GachaType;
import com.vwmin.terminalservice.CommandController;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.Reply;
import com.vwmin.terminalservice.autoconfigure.BotConfigurationProperties;
import com.vwmin.terminalservice.entity.PostEntity;
import com.vwmin.terminalservice.entity.ReplyEntity;
import lombok.SneakyThrows;
import org.springframework.data.util.Pair;

import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/21 12:57
 */
@CommandController(bind = "!扩充十连a")
public class ExpansionSupplyACommand implements Reply {

    private final
    GachaOperator operator;

    public ExpansionSupplyACommand(GachaOperator operator) {
        this.operator = operator;
    }

    @SneakyThrows
    @Override
    public ReplyEntity call(PostEntity postEntity) {
        Person person = new Person();
        Pair<GachaType, List<Item>> pair = operator.expansionA10(person);
        String draw = operator.draw(pair.getFirst(), pair.getSecond());
        return new ReplyEntity(new MessageSegmentBuilder().image(draw, null).build());
    }
}
