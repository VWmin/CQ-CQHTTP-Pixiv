package com.vwmin.coolq.pixiv;

import com.vwmin.coolq.pixiv.entities.Illust;
import com.vwmin.terminalservice.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import static com.vwmin.coolq.pixiv.IllustUtils.genFileName;
import static com.vwmin.coolq.pixiv.IllustUtils.getMetaSinglePage;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/19 12:12
 */
@Slf4j
public class DownloadTask implements Runnable{
    private final Header HEADER = new BasicHeader("Referer", "https://app-api.pixiv.net/");
    private final long id;
    private final String filename;
    private final String url;
    private final Semaphore semaphore;


    public DownloadTask(Illust illust){
        this(illust, null);
    }

    public DownloadTask(Illust illust, Semaphore semaphore){
        this.id = illust.getId();
        this.filename = genFileName(illust);
        this.url = getMetaSinglePage(illust);
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        log.info("downloading >> " + id);
        try {
            ImageUtils.downloadImage(filename, url, new Header[]{HEADER});
        } catch (IOException e) {
            log.warn("download fail >> " + id);
            e.printStackTrace();
        } finally {
            if (semaphore != null){
                semaphore.release();
            }
        }
    }
}
