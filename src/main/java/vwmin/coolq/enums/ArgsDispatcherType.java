package vwmin.coolq.enums;

public enum  ArgsDispatcherType {
    PIXIV("pixivArgsDispatcher"),
    SAUCENAO("saucenaoDispatcher"),
    DOWNLOAD("downloadDispatcher");

    private String key;

    ArgsDispatcherType(String key){
        this.key = key;
    }

    public String getKey(){return key;}
}