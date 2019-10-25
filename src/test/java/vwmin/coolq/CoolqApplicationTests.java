package vwmin.coolq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.function.pixiv.service.ScheduleTask;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CoolqApplicationTests {

    @Autowired
    ScheduleTask scheduleTask;

    @Autowired
    BotConfig botConfig;

    @Test
    public void contextLoads() {
//        scheduleTask.download();
    }

}
