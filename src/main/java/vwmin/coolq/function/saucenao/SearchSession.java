package vwmin.coolq.function.saucenao;

import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.enums.MessageType;
import vwmin.coolq.function.saucenao.entity.SaucenaoEntity;
import vwmin.coolq.function.saucenao.util.SaucenaoConsumer;
import vwmin.coolq.session.BaseSession;

import java.io.IOException;

public class SearchSession extends BaseSession {

    public SearchSession(Long userId, Long sourceId, MessageType messageType) {
        super(userId, sourceId, messageType);

    }

    @Override
    public void update(Long sourceId, String messageType, String[] args) {
    }

    @Override
    public SendMessageEntity checkAndExecute() throws IOException {
        SendMessageEntity send;

        SaucenaoEntity searchResponse = (SaucenaoEntity) command.execute();
        SaucenaoConsumer consumer = new SaucenaoConsumer(searchResponse, userId);
        send = SendMessageEntity.create(messageType, sourceId, consumer.mostly());
        close();

        return send;
    }

    @Override
    public ArgsDispatcherType getBelong() {
        return ArgsDispatcherType.SAUCENAO;
    }
}
