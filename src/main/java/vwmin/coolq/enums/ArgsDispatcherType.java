package vwmin.coolq.enums;

public enum  ArgsDispatcherType {
    PIXIV("pixivArgsDispatcher"),
    SAUCENAO("saucenaoDispatcher"),
    SETU("setuArgsDispatcher");

    private String key;

    ArgsDispatcherType(String key){
        this.key = key;
    }

    public String getKey(){return key;}

    @Override
    public String toString() {
        return key;
    }
}
