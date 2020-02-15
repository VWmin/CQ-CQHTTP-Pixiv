package vwmin.coolq.service;

@Deprecated
public class SimpleMessageSender implements MessageSender {
    private volatile static SimpleMessageSender instance;

    public static SimpleMessageSender getInstance() {
        if (instance == null){
            synchronized (SimpleMessageSender.class){
                if (instance == null){
                    return new SimpleMessageSender();
                }
            }
        }
        return instance;
    }




    @Override
    public void send() {

    }
}
