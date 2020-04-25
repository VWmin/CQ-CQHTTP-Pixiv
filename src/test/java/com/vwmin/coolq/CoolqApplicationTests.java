package com.vwmin.coolq;

import com.vwmin.coolq.honkar3rd.ResourceManager;
import com.vwmin.coolq.honkar3rd.entities.Item;
import com.vwmin.coolq.honkar3rd.entities.Person;
import com.vwmin.coolq.honkar3rd.gacha.CharacterSupply;
import com.vwmin.coolq.honkar3rd.gacha.EquipmentSupply;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootTest
public class CoolqApplicationTests {

    @Autowired
    ResourceManager manager;

    @Autowired
    CharacterSupply characterSupply;

    @Autowired
    EquipmentSupply equipmentSupply;

    @Test
    public void contextLoads() throws IOException {
        Person person = new Person();
        List<Item> res = new ArrayList<>();
        final int ccc = 10000;
        Map<String, Integer> map = new HashMap<>();
        for(int i=0; i<ccc; i++){
            Item next = equipmentSupply.next(person);
//            Item rubbish = focusedSupply.nextRubbish();

            res.add(next);
            if (!map.containsKey(next.getRealName())){
                map.put(next.getRealName(), 1);
            }else {
                Integer integer = map.get(next.getRealName());
                map.put(next.getRealName(), integer+1);
            }
//            res.add(rubbish);
        }

        map.forEach((k, v)-> System.out.println(k + " >>> " + v));
    }


    @Test
    public void testApi(){
    }

}
