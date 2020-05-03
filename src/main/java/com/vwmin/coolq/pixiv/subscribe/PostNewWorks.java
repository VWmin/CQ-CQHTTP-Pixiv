package com.vwmin.coolq.pixiv.subscribe;

import com.vwmin.coolq.common.Utils;
import com.vwmin.coolq.pixiv.entities.Illust;
import com.vwmin.coolq.pixiv.IllustUtils;
import com.vwmin.terminalservice.CQClientApi;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.SendEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.vwmin.coolq.pixiv.IllustUtils.genFileName;
import static com.vwmin.coolq.pixiv.IllustUtils.getMetaSinglePage;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/16 23:21
 */
@Slf4j
@Service
public class PostNewWorks implements ApplicationListener<NewWorksEvent> {

    private final
    CQClientApi api;

    public PostNewWorks(CQClientApi api) {
        this.api = api;
    }


    @Override
    public void onApplicationEvent(NewWorksEvent event) {
        List<Illust> illusts = event.getIllusts();
        Long who = event.getUserId();


        try {
            IllustUtils.asynchronousDownload(illusts, 0, illusts.size());
            MessageSegmentBuilder msgBuilder = new MessageSegmentBuilder();
            msgBuilder.plainText("快来看看咱进的新货\n");
            illusts.forEach((illust) -> msgBuilder
                    .plainText("来自【"+ illust.getUser().getName() +"】的【"+ illust.getTitle() +"】")
                    .image(genFileName(illust), getMetaSinglePage(illust))
                    .plainText("\n"));

            api.sendPrivateMsg(new SendEntity.PrivateSendEntity(who, msgBuilder.build()));
        } catch (IOException e) {
            log.warn("下载新作失败: " + e.getMessage());
            e.printStackTrace();

            Long me = 1903215898L;
            api.sendPrivateMsg(new SendEntity.PrivateSendEntity(me, Utils.handleException(e)));
        }



    }

}
