package com.vwmin.coolq.pixiv;

import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.MessageSegment;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

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

        List<Illust> illusts = response.getIllusts();
        illusts.removeIf(next -> !TYPE_ILLUST.equals(next.getType()));

        asynchronousDownload(illusts, offset, PAGE_SIZE);

        int cnt = 0;
        for(int i = offset; cnt != PAGE_SIZE && i < illusts.size(); i++){
            Illust illust = illusts.get(i);
            cnt++;
            builder.plainText(cnt+". "+illust.getTitle()
                    + "  view: " + illust.getTotal_view() + " like: " + illust.getTotal_bookmarks()
                    + "\n");
            builder.image(genFileName(illust), getMetaSinglePage(illust)).plainText("\n");
        }

        offset += cnt;
        //fixme: 当offset大于30时，应当请求next_url
        if(offset == MAX_SIZE || offset >= illusts.size()){
            needRefresh = true;
            offset = 0;
        }

        return builder.build();
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
