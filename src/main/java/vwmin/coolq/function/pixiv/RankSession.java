package vwmin.coolq.function.pixiv;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.util.IllustsResponseConsumer;
import vwmin.coolq.session.*;

import java.io.IOException;
import java.util.List;


public class RankSession extends BaseSession implements WithDataState {

    private DataState hasDataState;
    private DataState noDataState;

    private DataState dataState;

    public RankSession(Long userId, Long sourceId, String messageType){
        super(userId, sourceId, messageType);
        hasDataState = new HasDataState(this);
        noDataState = new NoDataState(this);

        dataState = noDataState;
    }


    @Override
    public void setData(Object data){
        dataState.setData(data);
    }

    @Override
    public Object getNext() throws IOException {
        return dataState.getNext();
    }

    @Override
    public void update(Long sourceId, String messageType, String[] args){

    }


    @Override
    public SendMessageEntity checkAndExecute() throws IOException {

        if (command instanceof NextCommand){
            // noinspection unchecked
            return new SendMessageEntity(messageType, sourceId, (List<MessageSegment>) getNext());
        }

        SendMessageEntity send;
        ListIllustResponse rankResponse = (ListIllustResponse) command.execute();
        consumer = new IllustsResponseConsumer(rankResponse, userId);
        // 获得数据后转到HasDataState
        setData(consumer);
        // TODO: 2020/2/6 如何通过命令指定响应的格式
        send = new SendMessageEntity(messageType, sourceId, ((IllustsResponseConsumer)consumer).top10());
        return send;
    }

    @Override
    public ArgsDispatcherType getBelong() {
        return ArgsDispatcherType.PIXIV;
    }

    @Override
    public void setDataState(DataState dataState) {
        this.dataState = dataState;
    }

    @Override
    public DataState getHasDataState() {
        return hasDataState;
    }

    @Override
    public void setHasDataState(DataState hasDataState) {
        this.hasDataState = hasDataState;
    }

    @Override
    public DataState getNoDataState() {
        return noDataState;
    }

    @Override
    public void setNoDataState(DataState noDataState) {
        this.noDataState = noDataState;
    }
}
