package com.vwmin.coolq;

import com.vwmin.coolq.common.RedisUtil;
import com.vwmin.coolq.pixiv.Illusts;
import com.vwmin.coolq.pixiv.PixivApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CoolqApplicationTests {

    @Autowired
    PixivApi api;

    @Test
    public void contextLoads() {
        RedisUtil.add2Set("old", 123L);
        Boolean old = RedisUtil.isMember("old", 123);
        System.out.println(old);
    }

    @Test
    public void initData(){
        final String KEY = "old";
        api.getNewWorks().getIllusts().forEach((illust) ->{
            if (!RedisUtil.isMember(KEY, illust.getId())){
                RedisUtil.add2Set(KEY, illust.getId());
            }
        });
    }

    @Test
    public void testApi(){
        Illusts newWorks = api.getNewWorks();
        System.out.println(newWorks);

    }

}
