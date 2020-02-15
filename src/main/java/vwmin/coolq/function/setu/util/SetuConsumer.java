package vwmin.coolq.function.setu.util;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.setu.entity.SetuEntity;
import vwmin.coolq.util.BaseConsumer;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.io.IOException;
import java.util.List;

public class SetuConsumer implements BaseConsumer {
    private final SetuEntity setuEntity;
    private final Long userId;

    public SetuConsumer(SetuEntity setuEntity, Long userId){
        this.setuEntity = setuEntity;
        this.userId = userId;
    }

    public List<MessageSegment> getOne() throws IOException {
        SetuEntity.DataBean dataBean = setuEntity.getData().get(0);
        String imgUrl = dataBean.getUrl();
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        return builder.addAtSegment(userId)
                .addImageSegment(dataBean.getPid()+imgUrl.substring(imgUrl.length()-4), imgUrl)
                .build();
    }


}
