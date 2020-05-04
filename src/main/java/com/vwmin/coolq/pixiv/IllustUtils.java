package com.vwmin.coolq.pixiv;

import com.vwmin.coolq.common.Utils;
import com.vwmin.coolq.pixiv.entities.Illust;
import com.vwmin.terminalservice.ImageUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 13:21
 */
@Slf4j
public class IllustUtils {
    private IllustUtils(){}

    public static String getMetaSinglePage(Illust illust){
        if(illust.getPage_count() > 1){
            return illust.getMeta_pages().get(0).getImage_urls().getOriginal();
        }else{
            return illust.getMeta_single_page().getOriginal_image_url();
        }
    }

    public static String getImgType(Illust illust){
        String str;
        if(illust.getPage_count() > 1){
            str = illust.getMeta_pages().get(0).getImage_urls().getOriginal();
        }else{
            str = illust.getMeta_single_page().getOriginal_image_url();
        }
        return str.substring(str.length()-4);
    }

    public static String genFileName(Illust illust){
        int id = illust.getId();
        return id+getImgType(illust);
    }


    public static void asynchronousDownload(List<Illust> illusts, int offset, int pageSize) throws IOException {
        Utils.notEmpty(illusts, "任务列表为空");
        Semaphore semaphore = new Semaphore(0);

        /*
         * 核心线程池大小
         * 最大线程池大小
         * 线程池最大空闲时间
         * 时间单位
         * 线程等待队列
         * 线程创建工厂
         * 拒绝策略
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                pageSize,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(pageSize),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        log.info("starting thread...");

        int cnt=0;
        for (int i=offset; cnt != pageSize && i<illusts.size(); i++) {
            Illust illust = illusts.get(i);
            String filename = genFileName(illust);

            cnt++;
            if (!ImageUtils.isExist(filename)) {
                pool.execute(new PixivDownloadTask(illust, semaphore));
            } else {
                log.info("file exist, passed >> " + illust.getId());
                semaphore.release();
            }

        }

        try {
            log.info("waiting all thread to accomplish...");
            semaphore.tryAcquire(cnt, 180, TimeUnit.SECONDS);
            log.info("all thread accomplished, download task over.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
