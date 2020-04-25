package com.vwmin.coolq.setu;

import com.vwmin.coolq.common.RedisUtil;
import com.vwmin.terminalservice.CQClientApi;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.SendEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    @Scheduled(cron = "0 30 22 * * ? ")
    public void statistic(){
        Map<String, Long> map = RedisUtil.kvMap(CACHE_NAME);
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        builder.plainText("今日涩图小能手 >> \n");
        final int[] i = {1};
        map.forEach((k, v)->{
            builder.plainText(i[0] +". " + k + ": " + v + "次\n");
            RedisUtil.setToZero(CACHE_NAME, k);
            i[0] = i[0] +1;
        });

        clientApi.sendGroupMsg(new SendEntity.GroupSendEntity(570500496L, builder.build()));
    }
}
