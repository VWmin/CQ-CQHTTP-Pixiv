package vwmin.coolq.function.pixiv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vwmin.coolq.configuration.BotConfig;

@Slf4j
@Component
//@EnableScheduling
public class ScheduleTask {

    private final PixivService pixivService;
    private final BotConfig botConfig;

    private final String[] RANK_MODE_LIST = {"day", "week", "month", "day_male", "week_original",
            "week_rookie", "day_r18", "day_male_r18", "week_r18"};

    public ScheduleTask(PixivService pixivService, BotConfig botConfig){
        this.pixivService = pixivService;
        this.botConfig = botConfig;
    }

//    @Scheduled(cron = "0 0 6 1/1 * ?")
//    public void autoDownload(){
//        download();
//    }

//    public void download(){
//        Semaphore semaphore = new Semaphore(0);
//        for(String mode : RANK_MODE_LIST){
//            log.info("mode " + mode + " 自动缓存开始");
//            Thread taskThread = new Thread(() -> {
//                Semaphore semaphore2 = new Semaphore(0);
//                ListIllustResponse rank = pixivService.getRank(mode, null);
//                if(rank == null || rank.getIllusts() == null){
//                    log.warn("mode " + mode + " 自动缓存失败");
//                    return;
//                }
//                int cnt=0;
//                for(int i=0; i<rank.getIllusts().size(); i++){
//
//                    ListIllustResponse.IllustsBean illust = rank.getIllusts().get(i);
//                    if(illust.getType().equals("illust")){
//                        cnt++;
//                        if(!tryLocal(botConfig.getImagePath(), illust.getId()+getImgType(illust))){
//                            Thread downloadThread = new Thread(()-> {
//                                log.info("downloading >> " + illust.getId());
//                                downloadPixivImage(botConfig.getImagePath(),
//                                        illust.getId()+getImgType(illust), getMetaSinglePage(illust));
//                                semaphore2.release();
//                            });
//                            downloadThread.start();
//                        }else{
//                            log.info("passed >> " + illust.getId());
//                            semaphore2.release();
//                        }
//                        if(cnt == 10) break;
//                    }
//                }
//                try {
//                    semaphore2.tryAcquire(10, 120, TimeUnit.SECONDS);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                semaphore.release();
//            });
//            taskThread.start();
//
//        }
//
//        try {
//            semaphore.acquire(RANK_MODE_LIST.length);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        // TODO: 2019/10/25 还有一种情况 一张图同时存在于多个榜单中，下载的时候可能某个线程先拿到句柄，正在写入的过程中其他线程又检测到文件不存在，尝试打开句柄的时候就会出现错误
//    }
}
