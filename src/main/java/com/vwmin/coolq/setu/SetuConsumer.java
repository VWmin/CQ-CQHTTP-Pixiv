package com.vwmin.coolq.setu;

import com.vwmin.coolq.common.Utils;
import com.vwmin.coolq.exception.EmptyDataException;
import com.vwmin.coolq.exception.UnexpectedStatusCodeException;
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

    public SetuConsumer(SetuEntity setuEntity){
        this.setuEntity = setuEntity;
    }

    public List<MessageSegment> getOne() throws IOException {
        preCheck(setuEntity);
        SetuEntity.DataBean dataBean = setuEntity.getData().get(0);
        String imgUrl = dataBean.getUrl();
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        builder.image(dataBean.getPid()+imgUrl.substring(imgUrl.length()-4), imgUrl);
        if (setuEntity.getQuota() <= 5){
            builder.plainText("Alert! 掏空倒计时：" + setuEntity.getQuota());
        }
        return builder.build();
    }

    private void preCheck(SetuEntity entity) throws EmptyDataException, UnexpectedStatusCodeException {
        Utils.notEmpty(setuEntity.getData(),"人家真的一张也没有了");
        if (entity.getCode() != 0){
            throw new UnexpectedStatusCodeException(entity.getCode(), entity.getMsg());
        }
    }


}
