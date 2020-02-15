package vwmin.coolq.function.setu;

import lombok.extern.slf4j.Slf4j;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.function.setu.entity.SetuEntity;
import vwmin.coolq.function.setu.util.SetuConsumer;
import vwmin.coolq.session.BaseSession;

import java.io.IOException;

/**
 * @author Min
 */
@Slf4j
public class SetuSession extends BaseSession {


    public SetuSession(Long userId, Long sourceId, String messageType) {
        super(userId, sourceId, messageType);
    }

    @Override
    public void update(Long sourceId, String messageType, String[] args) {
    }

    @Override
    public SendMessageEntity checkAndExecute() throws IOException {
        SendMessageEntity send;

        SetuEntity setuEntity = (SetuEntity) command.execute();
        consumer = new SetuConsumer(setuEntity, userId);
        send = new SendMessageEntity(messageType, sourceId, ((SetuConsumer)consumer).getOne());
        close();

        return send;
    }

    @Override
    public ArgsDispatcherType getBelong() {
        return ArgsDispatcherType.SETU;
    }

    //session只作为命令的执行和数据的保持着
    //command需要在dispatcher中完成构建并传递给session执行
    //也就是说在dispatcher之后和session之前加入一层封装完成args到command的转化
}
