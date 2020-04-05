package vwmin.coolq;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.config.BotConfig;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.service.ScheduleTask;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CoolqApplicationTests {


    @Autowired
    BotConfig botConfig;

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void serviceDiscovery() throws NacosException {
//        ListIllustResponse forObject = restTemplate.getForObject(botConfig.getPixivApi() + "/illust/ranking", ListIllustResponse.class);
//        Assert.notNull(forObject, "哎");
    }

}
