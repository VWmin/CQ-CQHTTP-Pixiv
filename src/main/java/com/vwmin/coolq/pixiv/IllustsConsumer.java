package com.vwmin.coolq.pixiv;

import com.vwmin.terminalservice.ImageUtils;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.MessageSegment;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

import static com.vwmin.coolq.pixiv.IllustUtils.*;

/**
 * 处理收到的Response内容 返回发送消息所用的数据段
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 18:16
 */
@Slf4j
public class IllustsConsumer {

    private static final int MAX_SIZE = 30;
    private static final int PAGE_SIZE = 10;
    private static final String TYPE_ILLUST = "illust";

    private Illusts response;
    private final Long userId;
    private int offset = 0;
    private boolean needRefresh = false;

    public IllustsConsumer(Illusts response, Long userId){
        this.response = response;
        this.userId = userId;
    }



    public List<MessageSegment> top10() throws IOException {
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        if(response == null || response.getIllusts() == null || response.getIllusts().size() == 0){
            throw new IOException("看样子没找到任何结果呢 等会再试试看？");
        }

        List<Illusts.IllustsBean> illusts = response.getIllusts();
        illusts.removeIf(next -> !TYPE_ILLUST.equals(next.getType()));

        asynchronousDownload();

        int cnt = 0;
        for(int i = offset; cnt != PAGE_SIZE && i < illusts.size(); i++){
            Illusts.IllustsBean illust = illusts.get(i);
            cnt++;
            builder.plainText(cnt+". "+illust.getTitle() + "  view: " + illust.getTotal_view() + " like: " + illust.getTotal_bookmarks() + "\n");
            builder.image(illust.getId()+ getImgType(illust), getMetaSinglePage(illust)).plainText("\n");
        }

        offset += cnt;
        //fixme: 当offset大于30时，应当请求next_url
        if(offset == MAX_SIZE || offset >= illusts.size()){
            needRefresh = true;
            offset = 0;
        }

        return builder.build();
    }

    private static class DownLoadTask implements Runnable{
        private final Header HEADER = new BasicHeader("Referer", "https://app-api.pixiv.net/");
        private final long id;
        private final String filename;
        private final String url;
        private final Semaphore semaphore;

        DownLoadTask(long id, String filename, String url, Semaphore semaphore){
            this.id = id;
            this.filename = filename;
            this.url = url;
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
                semaphore.release();
            }
        }
    }

    private void asynchronousDownload() throws IOException {
        List<Illusts.IllustsBean> illusts = response.getIllusts();
        Semaphore semaphore = new Semaphore(0);

        /*
         * 核心线程池大小
         * 最大线程池大小
         * 线程池最大空闲持剑
         * 时间单位
         * 线程等待队列
         * 线程创建工厂
         * 拒绝策略
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,
                10,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        int cnt=0;
        for (int i=offset; cnt != PAGE_SIZE && i<illusts.size(); i++) {
            Illusts.IllustsBean illust = illusts.get(i);
            String filename = illust.getId() + getImgType(illust);
            String url = getMetaSinglePage(illust);
            int id = illust.getId();

            cnt++;
            if (!ImageUtils.isExist(filename)) {
                pool.execute(new DownLoadTask(id, filename, url, semaphore));
            } else {
                log.info("passed >> " + illust.getId());
                semaphore.release();
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

    public void setResponse(Illusts response) {
        this.response = response;
    }


    public List<MessageSegment> getNext() throws IOException {
        return top10();
    }
}
