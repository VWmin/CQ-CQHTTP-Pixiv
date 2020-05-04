package com.vwmin.coolq;

import com.vwmin.coolq.pixiv.PixivApi;
import com.vwmin.coolq.pixiv.entities.Illust;
import com.vwmin.coolq.setu.Statistician;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@Slf4j
@SpringBootTest
public class CoolqApplicationTests {


    @Test
    public void contextLoads() {

    }


    @Autowired
    PixivApi pixivApi;

    @Test
    public void testApi(){
        Illust illustById = pixivApi.getIllustById(12345678).getIllust();
        System.out.println(illustById);
    }

    @Autowired
    Statistician statistician;

    @Test void testStatistic(){
        statistician.statistic();
    }

}
