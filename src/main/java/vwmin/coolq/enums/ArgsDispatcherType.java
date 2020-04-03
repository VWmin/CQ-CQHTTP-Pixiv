package vwmin.coolq.enums;

/**
 * @author Min
 */

public enum  ArgsDispatcherType {
    /***/
    PIXIV("pixivArgsDispatcher"),
    SAUCENAO("saucenaoDispatcher"),
    SETU("setuArgsDispatcher"),
    NULL("nullArgsDispatcher");

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
