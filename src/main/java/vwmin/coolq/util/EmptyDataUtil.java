package vwmin.coolq.util;

import vwmin.coolq.exception.EmptyDataException;

import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/3 13:01
 */
public class EmptyDataUtil {

    private static final String DEFAULT_MSG = "空的数据集";

    public static void assertListNotEmpty(List list, String msg) throws EmptyDataException {
        if (list == null || list.size() == 0){
            throw new EmptyDataException(msg);
        }
    }

    public static void assertListNotEmpty(List list) throws EmptyDataException {
        assertListNotEmpty(list, DEFAULT_MSG);
    }
}
