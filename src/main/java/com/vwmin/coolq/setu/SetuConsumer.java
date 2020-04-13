package com.vwmin.coolq.setu;

import com.vwmin.coolq.common.Utils;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.MessageSegment;

import java.io.IOException;
import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 17:39
 */
public class SetuConsumer {
    private final SetuEntity setuEntity;
    private final Long userId;

    public SetuConsumer(SetuEntity setuEntity, Long userId){
        this.setuEntity = setuEntity;
        this.userId = userId;
    }

    public List<MessageSegment> getOne() throws IOException {
        Utils.notEmpty(setuEntity.getData(),"人家真的一张也没有了");
        SetuEntity.DataBean dataBean = setuEntity.getData().get(0);
        String imgUrl = dataBean.getUrl();
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        return builder.image(dataBean.getPid()+imgUrl.substring(imgUrl.length()-4), imgUrl)
                .build();
    }


}
