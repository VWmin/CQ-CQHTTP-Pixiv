package vwmin.coolq.function.pixiv;

import vwmin.coolq.function.WithDataState;

import java.io.IOException;

public class NoDataState implements DataState {

    private WithDataState session;

    public NoDataState(WithDataState session){
        this.session = session;
    }


    @Override
    public void setData(Object data) {
        DataState hasDataState = session.getHasDataState();
        hasDataState.setData(data);
        session.setDataState(hasDataState);
    }

    @Override
    public Object getNext() throws IOException {
        throw new IOException("没有更多数据");
    }
}
