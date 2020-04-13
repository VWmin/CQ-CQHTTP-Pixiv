package com.vwmin.coolq.function.pixiv;

//import vwmin.coolq.entity.MessageSegment;
//import vwmin.coolq.entity.SendMessageEntity;
//import vwmin.coolq.enums.ArgsDispatcherType;
//import vwmin.coolq.enums.MessageType;
//import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
//import vwmin.coolq.function.pixiv.util.IllustsResponseConsumer;
//import vwmin.coolq.session.BaseSession;
//import vwmin.coolq.function.WithDataState;
//
//import java.io.IOException;
//import java.util.List;
//
//public class WordSession extends BaseSession implements WithDataState {
//    private DataState hasDataState;
//    private DataState noDataState;
//
//    private DataState dataState;
//
//    public WordSession(Long userId, Long sourceId, MessageType messageType) {
//        super(userId, sourceId, messageType);
//        hasDataState = new HasDataState(this);
//        noDataState = new NoDataState(this);
//
//        dataState = noDataState;
//    }
//
//    @Override
//    public void update(Long sourceId, String messageType, String[] args) {
//
//    }
//    @Override
//    public SendMessageEntity checkAndExecute() throws IOException {
//        SendMessageEntity send;
//
//        if (command instanceof NextCommand){
//            // noinspection unchecked
//            return SendMessageEntity.create(messageType, sourceId, (List<MessageSegment>) getNext());
//        }
//
//        ListIllustResponse rankResponse = (ListIllustResponse) command.execute();
//        consumer = new IllustsResponseConsumer(rankResponse, userId);
//        setData(consumer);
//        send = SendMessageEntity.create(messageType, sourceId, ((IllustsResponseConsumer)consumer).top10());
//
//        return send;
//    }
//
//    @Override
//    public ArgsDispatcherType getBelong() {
//        return ArgsDispatcherType.PIXIV;
//    }
//
//    @Override
//    public void setData(Object data) {
//        dataState.setData(data);
//    }
//
//    @Override
//    public Object getNext() throws IOException {
//        return dataState.getNext();
//    }
//
//    @Override
//    public void setDataState(DataState dataState) {
//        this.dataState = dataState;
//    }
//
//    @Override
//    public DataState getHasDataState() {
//        return hasDataState;
//    }
//
//    @Override
//    public void setHasDataState(DataState hasDataState) {
//        this.hasDataState = hasDataState;
//    }
//
//    @Override
//    public DataState getNoDataState() {
//        return noDataState;
//    }
//
//    @Override
//    public void setNoDataState(DataState noDataState) {
//        this.noDataState = noDataState;
//    }
//}
