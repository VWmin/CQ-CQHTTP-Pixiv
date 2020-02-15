package vwmin.coolq.session;

import vwmin.coolq.function.pixiv.DataState;

import java.io.IOException;

public interface WithDataState {
    void setData(Object data);

    Object getNext() throws IOException;


    void setDataState(DataState dataState);


    DataState getHasDataState();

    void setHasDataState(DataState hasDataState);

    DataState getNoDataState();

    void setNoDataState(DataState noDataState);
}
