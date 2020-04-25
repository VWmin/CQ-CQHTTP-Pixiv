package com.vwmin.coolq.honkar3rd;

import com.vwmin.coolq.honkar3rd.entities.Item;
import com.vwmin.coolq.honkar3rd.entities.Person;
import com.vwmin.coolq.honkar3rd.gacha.BaseSupply;
import com.vwmin.coolq.honkar3rd.gacha.CharacterSupply;
import com.vwmin.coolq.honkar3rd.gacha.EquipmentSupply;
import com.vwmin.coolq.honkar3rd.gacha.GachaType;
import com.vwmin.terminalservice.autoconfigure.BotConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/25 18:05
 */
@Service
public class GachaOperator {


    private final
    CharacterSupply expansionSupplyA;

    private final
    EquipmentSupply focusedSupplyA;

    private final
    EquipmentSupply focusedSupplyB;

    private final
    EquipmentSupply expansionSupplyB;

    private final
    ResourceManager manager;

    private final
    BotConfigurationProperties properties;

    public GachaOperator(@Qualifier("expansionSupplyA") CharacterSupply expansionSupplyA,
                         @Qualifier("expansionSupplyB") EquipmentSupply expansionSupplyB,
                         @Qualifier("focusedSupplyA") EquipmentSupply focusedSupplyA,
                         @Qualifier("focusedSupplyB") EquipmentSupply focusedSupplyB,
                         ResourceManager manager, BotConfigurationProperties properties) {
        this.expansionSupplyA = expansionSupplyA;
        this.expansionSupplyB = expansionSupplyB;
        this.focusedSupplyA = focusedSupplyA;
        this.focusedSupplyB = focusedSupplyB;
        this.manager = manager;
        this.properties = properties;
    }

    public Pair<GachaType, List<Item>> focusedA10(Person person){
        List<Item> res = chouchouchou(focusedSupplyA, person);
        return Pair.of(GachaType.FOCUSED_A_10, res);
    }

    public Pair<GachaType, List<Item>> focusedB10(Person person){
        List<Item> res = chouchouchou(focusedSupplyB, person);
        return Pair.of(GachaType.FOCUSED_B_10, res);
    }

    public Pair<GachaType, List<Item>> expansionA10(Person person){
        List<Item> res = chouchouchou(expansionSupplyA, person);
        return Pair.of(GachaType.EXPANSION_A_10, res);
    }

    public Pair<GachaType, List<Item>> expansionB10(Person person){
        List<Item> res = chouchouchou(expansionSupplyB, person);
        return Pair.of(GachaType.EXPANSION_B_10, res);
    }

    public String draw(GachaType type, List<Item> result) throws IOException {
        return new ImageOperator(type, result, manager)
                .draw(properties.getCqHome() + "/data/image/");
    }


    private List<Item> chouchouchou(BaseSupply supply, Person person){
        List<Item> res = new ArrayList<>();
        for(int i=0; i<10; i++){
            Item next = supply.next(person);
            Item rubbish = supply.nextRubbish();

            res.add(next);
            res.add(rubbish);
        }
        return res;
    }
}
