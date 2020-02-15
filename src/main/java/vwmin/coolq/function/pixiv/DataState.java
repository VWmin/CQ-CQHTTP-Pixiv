package vwmin.coolq.function.pixiv;

import java.io.IOException;

/**
 * @author Min
 */
public interface DataState {
    /**
     * 从无数据状态到有数据状态
     */
    void setData(Object data);

    /**
     * 获取部分数据，数据指针后移，判断是否需要进入无数据状态
     */
    Object getNext() throws IOException;
}
