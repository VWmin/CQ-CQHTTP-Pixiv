package vwmin.coolq.enums;

public enum  SessionType {
    PIXIV("pixiv"),
    SAUCENAO("saucenao");

    private String key;

    SessionType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
