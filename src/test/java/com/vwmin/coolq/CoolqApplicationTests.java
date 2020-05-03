package com.vwmin.coolq;

import com.vwmin.coolq.honkar3rd.ResourceManager;
import com.vwmin.coolq.honkar3rd.entities.Item;
import com.vwmin.coolq.honkar3rd.entities.Person;
import com.vwmin.coolq.honkar3rd.gacha.CharacterSupply;
import com.vwmin.coolq.honkar3rd.gacha.EquipmentSupply;
import com.vwmin.coolq.setu.Statistician;
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


    @Test
    public void contextLoads() {

    }


    @Test
    public void testApi(){
    }

    @Autowired
    Statistician statistician;

    @Test void testStatistic(){
        statistician.statistic();
    }

}
