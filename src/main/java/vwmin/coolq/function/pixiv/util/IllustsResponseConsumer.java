package vwmin.coolq.function.pixiv.util;

import lombok.extern.slf4j.Slf4j;
import vwmin.coolq.SpringUtil;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.util.BaseConsumer;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static vwmin.coolq.function.pixiv.util.IllustUtil.getImgType;
import static vwmin.coolq.function.pixiv.util.IllustUtil.getMetaSinglePage;
import static vwmin.coolq.util.DownloadUtil.downloadPixivImage;
import static vwmin.coolq.util.DownloadUtil.tryLocal;

/**
 * 处理收到的Response内容 返回发送消息所用的数据段
 */
@Slf4j
public class IllustsResponseConsumer implements BaseConsumer {

    private ListIllustResponse response;
    private final Long user_id;
    private int offset = 0;
    private String rankType;
    private boolean needRefresh = false;

    public IllustsResponseConsumer(ListIllustResponse response, Long user_id, String rankType){
        this.response = response;
        this.rankType = rankType;
        this.user_id = user_id;
    }



    public List<MessageSegment> top10(boolean isShowDetail){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        if(response == null || response.getIllusts() == null || response.getIllusts().size() == 0){
            builder.addAtSegment(user_id).addTextSegment("看样子没找到任何结果呢 等会再试试看？");
            return builder.build();
        }
        List<ListIllustResponse.IllustsBean> illusts = response.getIllusts();

        builder.addAtSegment(user_id).addTextSegment("\n");
        if(!"none".equals(rankType)) builder.addTextSegment("当前排行榜类型："+rankType+"\n");

        if(isShowDetail) {
            try {
                asynchronousDownload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int cnt=0;
        for(int i=offset; i<illusts.size(); i++){
            ListIllustResponse.IllustsBean illust = illusts.get(i);
            if(illust.getType().equals("illust")){
                cnt++;
                builder.addTextSegment(cnt+". "+illust.getTitle() + "  view: " + illust.getTotal_view() + " like: " + illust.getTotal_bookmarks() + "\n");
                if(isShowDetail) builder.addImageSegment(illust.getId()+getImgType(illust), getMetaSinglePage(illust)).addTextSegment("\n");
                else builder.addTextSegment("\n");
            }
            if(cnt == 10) break;
        }
        offset+=10;
        //todo: 当offset大于30时，应当请求next_url
        if(offset == 30){
            needRefresh = true;
            offset = 0;
        }

        return builder.build();
    }

    private void asynchronousDownload() throws Exception {
        BotConfig botConfig =  (BotConfig) SpringUtil.getBean("botConfig");
        if (botConfig == null){
            throw new Exception("配置读取错误");
        }
        List<ListIllustResponse.IllustsBean> illusts = response.getIllusts();
        Semaphore semaphore = new Semaphore(0);
        int cnt=0;
        for (int i=offset; i<illusts.size(); i++) {
            ListIllustResponse.IllustsBean illust = illusts.get(i);
            if (illust.getType().equals("illust")) {
                cnt++;
                if (!tryLocal(botConfig.getImagePath(), illust.getId() + getImgType(illust))) {
                    Thread downloadThread = new Thread(() -> {
                        log.info("downloading >> " + illust.getId());
                        downloadPixivImage(botConfig.getImagePath(),
                                illust.getId() + getImgType(illust), getMetaSinglePage(illust));
                        semaphore.release();
                    });
                    downloadThread.start();
                } else {
                    log.info("passed >> " + illust.getId());
                    semaphore.release();
                }
                if (cnt == 10) break;
            }
        }

        try {
            semaphore.tryAcquire(cnt, 180, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean isNeedRefresh() {
        return needRefresh;
    }

    public void setNeedRefresh(boolean needRefresh) {
        this.needRefresh = needRefresh;
    }

    public String getNextUrl(){
        return response.getNext_url();
    }

    public void setResponse(ListIllustResponse response) {
        this.response = response;
    }
}
