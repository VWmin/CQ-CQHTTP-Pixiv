package com.vwmin.coolq.function.pixiv.util;

//import lombok.extern.slf4j.Slf4j;
//import vwmin.coolq.config.BotConfig;
//import vwmin.coolq.entity.MessageSegment;
//import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
//import vwmin.coolq.core.BaseConsumer;
//import vwmin.coolq.core.MessageSegmentBuilder;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.TimeUnit;
//
//import static vwmin.coolq.function.pixiv.util.IllustUtil.getImgType;
//import static vwmin.coolq.function.pixiv.util.IllustUtil.getMetaSinglePage;
//import static vwmin.coolq.util.DownloadUtil.downloadPixivImage;
//import static vwmin.coolq.util.DownloadUtil.tryLocal;
//
///**
// * 处理收到的Response内容 返回发送消息所用的数据段
// */
//@Slf4j
//public class IllustsResponseConsumer implements BaseConsumer {
//
//    private static final int MAX_SIZE = 30;
//    private static final int PAGE_SIZE = 10;
//    private static final String TYPE_ILLUST = "illust";
//
//    private ListIllustResponse response;
//    private final Long userId;
//    private int offset = 0;
//    private boolean needRefresh = false;
//
//    public IllustsResponseConsumer(ListIllustResponse response, Long userId){
//        this.response = response;
//        this.userId = userId;
//    }
//
//
//
//    public List<MessageSegment> top10() throws IOException {
//        MessageSegmentBuilder builder = new MessageSegmentBuilder();
//        if(response == null || response.getIllusts() == null || response.getIllusts().size() == 0){
//            throw new IOException("看样子没找到任何结果呢 等会再试试看？");
//        }
//
//        List<ListIllustResponse.IllustsBean> illusts = response.getIllusts();
//        illusts.removeIf(next -> !TYPE_ILLUST.equals(next.getType()));
//
//        builder.addAtSegment(userId).addTextSegment("\n");
//
//        asynchronousDownload();
//
//        int cnt = 0;
//        for(int i = offset; cnt != PAGE_SIZE && i < illusts.size(); i++){
//            ListIllustResponse.IllustsBean illust = illusts.get(i);
//            cnt++;
//            builder.addTextSegment(cnt+". "+illust.getTitle() + "  view: " + illust.getTotal_view() + " like: " + illust.getTotal_bookmarks() + "\n");
//            builder.addImageSegment(illust.getId()+getImgType(illust), getMetaSinglePage(illust)).addTextSegment("\n");
//        }
//
//        offset += cnt;
//        //fixme: 当offset大于30时，应当请求next_url
//        if(offset == MAX_SIZE || offset >= illusts.size()){
//            needRefresh = true;
//            offset = 0;
//        }
//
//        return builder.build();
//    }
//
//    private void asynchronousDownload() throws IOException {
//        BotConfig botConfig =  (BotConfig) SpringUtil.getBean("botConfig");
//        if (botConfig == null){
//            throw new IOException("配置读取错误");
//        }
//        List<ListIllustResponse.IllustsBean> illusts = response.getIllusts();
//        Semaphore semaphore = new Semaphore(0);
//
//        int cnt=0;
//        for (int i=offset; cnt != PAGE_SIZE && i<illusts.size(); i++) {
//            ListIllustResponse.IllustsBean illust = illusts.get(i);
//
//            cnt++;
//            if (!tryLocal(botConfig.getImagePath(), illust.getId() + getImgType(illust))) {
//                Thread downloadThread = new Thread(() -> {
//                    log.info("downloading >> " + illust.getId());
//                    try {
//                        downloadPixivImage(botConfig.getImagePath(),
//                                illust.getId() + getImgType(illust), getMetaSinglePage(illust));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } finally {
//                        semaphore.release();
//                    }
//                });
//                downloadThread.start();
//            } else {
//                log.info("passed >> " + illust.getId());
//                semaphore.release();
//            }
//
//        }
//
//        try {
//            semaphore.tryAcquire(cnt, 180, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public boolean isNeedRefresh() {
//        return needRefresh;
//    }
//
//    public void setNeedRefresh(boolean needRefresh) {
//        this.needRefresh = needRefresh;
//    }
//
//    public String getNextUrl(){
//        return response.getNext_url();
//    }
//
//    public void setResponse(ListIllustResponse response) {
//        this.response = response;
//    }
//
//
//    public List<MessageSegment> getNext() throws IOException {
////        if (!needRefresh){
//        return top10();
////        }
//    }
//}
