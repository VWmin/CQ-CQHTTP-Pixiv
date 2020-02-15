package vwmin.coolq.function.pixiv;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.pixiv.util.IllustsResponseConsumer;
import vwmin.coolq.session.WithDataState;
import vwmin.coolq.util.BaseConsumer;

import java.io.IOException;
import java.util.List;

public class HasDataState implements DataState {

    private WithDataState session;
    private IllustsResponseConsumer data;

    public HasDataState(WithDataState session){
        this.session = session;
    }

    @Override
    public void setData(Object data) {
        this.data = (IllustsResponseConsumer) data;
    }

    @Override
    public Object getNext() throws IOException {
        List<MessageSegment> next = data.getNext();
        if (data.isNeedRefresh()){
            session.setDataState(session.getNoDataState());
        }
        return next;
    }
}
