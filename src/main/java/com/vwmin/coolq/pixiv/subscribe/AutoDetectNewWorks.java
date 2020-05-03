package com.vwmin.coolq.pixiv.subscribe;


import com.google.gson.Gson;
import com.vwmin.coolq.pixiv.entities.Illust;
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

    static final String NEW_WORKS = "newWorks"; // QQ -> Set<IllustId>
    static final String SUBSCRIBERS = "subscribers"; // QQ -> pixiv username

    private final
    PixivApi api;

    private final
    ApplicationEventPublisher publisher;

    public AutoDetectNewWorks(PixivApi api, ApplicationEventPublisher publisher) {
        this.api = api;
        this.publisher = publisher;
    }


    @Scheduled(cron = "0 30 * * * ?")
    public void detect(){
        log.info("detecting new works...");
        Map<String, Object> members = RedisUtil.getCache(SUBSCRIBERS);
        members.forEach((k, v) ->{
            String name = v.toString();
            List<Illust> need2Post = new ArrayList<>();
            api.getNewWorks(name).getIllusts().forEach((illust) ->{
                if (!RedisUtil.isMember(NEW_WORKS, k, illust.getId())){
                    need2Post.add(illust);
                    RedisUtil.add2Set(NEW_WORKS, k, illust.getId());
                }
            });

            log.info("got {} new works for {}", need2Post.size(), k);

            if (need2Post.size() != 0) {
                publisher.publishEvent(new NewWorksEvent(this, Long.parseLong(k), need2Post)) ;
            }
        });

    }

}
