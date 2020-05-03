package com.vwmin.coolq.setu;

import com.vwmin.coolq.common.RedisUtil;
import com.vwmin.terminalservice.CQClientApi;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.SendEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/24 22:28
 */
@Slf4j
@Service
@EnableScheduling
public class Statistician {

    private static final String CACHE_NAME = "setu";

    final
    CQClientApi clientApi;

    public Statistician(CQClientApi clientApi) {
        this.clientApi = clientApi;
    }

    public void record(String name){
        RedisUtil.increase(CACHE_NAME, name);
    }

    @Scheduled(cron = "0 55 22 * * ? ")
    public void statistic(){
        Map<String, Object> map = RedisUtil.getCache(CACHE_NAME);
        List<Pair<String, Integer>> records = new ArrayList<>();

        map.forEach((k, v)->{
            if (((Integer) v) != 0){
                records.add(Pair.of(k, ((Integer) v)));
                RedisUtil.setToZero(CACHE_NAME, k);
            }
        });
        records.sort(((o1, o2) -> o2.getRight() - o1.getRight()));

        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        builder.plainText("今日涩图小能手 >> \n");
        int i = 1;
        for(Pair<String, Integer> record : records){
            builder.plainText(i++ +". "+ record.getLeft() +" >>> "+ record.getRight() +"次.\n");
        }

        clientApi.sendGroupMsg(new SendEntity.GroupSendEntity(570500496L, builder.build()));
    }
}
