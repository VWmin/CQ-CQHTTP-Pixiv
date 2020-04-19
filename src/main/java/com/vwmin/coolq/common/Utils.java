package com.vwmin.coolq.common;

import com.vwmin.coolq.exception.UnexpectedStatusCodeException;
import com.vwmin.terminalservice.CQClientApi;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.MessageSegment;
import com.vwmin.coolq.exception.EmptyDataException;

import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 17:42
 */
public class Utils {
    private Utils(){}

    private static final String DEFAULT_MSG = "空的数据集";

    public static void notEmpty(List<?> list, String msg) throws EmptyDataException {
        if (list == null || list.size() == 0){
            throw new EmptyDataException(msg);
        }
    }

    public static void notEmpty(List<?> list) throws EmptyDataException {
        notEmpty(list, DEFAULT_MSG);
    }

    public static void notNull(Object o, String msg){
        if (o == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    //包装错误信息成可发送的实体
    public static List<MessageSegment> handleException(Exception e){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        builder.plainText("Exception: \n");
        if (e instanceof UnexpectedStatusCodeException){
            builder.plainText("status code: "+((UnexpectedStatusCodeException) e).getCode() + "\t");
        }
        builder.plainText(e.getMessage());
        return builder.build();
    }
}
