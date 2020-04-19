package com.vwmin.coolq.pixiv.subscribe;


import com.vwmin.coolq.pixiv.Illust;
import com.vwmin.coolq.pixiv.Illusts;
import com.vwmin.coolq.pixiv.PixivApi;
import com.vwmin.coolq.common.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/16 23:13
 */
@Slf4j
@Service
@EnableScheduling
public class AutoDetectNewWorks {

    private static final String KEY = "old";

    private final
    PixivApi api;

    private final
    ApplicationEventPublisher publisher;

    public AutoDetectNewWorks(PixivApi api, ApplicationEventPublisher publisher) {
        this.api = api;
        this.publisher = publisher;
    }


    @Scheduled(cron = "0 0/30 * * * ?")
    public void detect(){
        log.info("detecting new works...");
        List<Illust> need2Post = new ArrayList<>();
        api.getNewWorks().getIllusts().forEach((illust) ->{
            if (!RedisUtil.isMember(KEY, illust.getId())){
                need2Post.add(illust);
                RedisUtil.add2Set(KEY, illust.getId());
            }
        });

        log.info("get new works size >>> " + need2Post.size());

        if (need2Post.size() != 0) {
            publisher.publishEvent(new NewWorksEvent(this, need2Post)) ;
        }
    }



}
