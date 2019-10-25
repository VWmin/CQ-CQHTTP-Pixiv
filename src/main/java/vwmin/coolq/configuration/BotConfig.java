package vwmin.coolq.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component("botConfig")
@ConfigurationProperties(prefix = "botconfig")
public class BotConfig {
    private String imagePath;
    private String pixivApi;
    private String cqClientUrl;

    public String getCqClientUrl() {
        return cqClientUrl;
    }

    public void setCqClientUrl(String cqClientUrl) {
        this.cqClientUrl = cqClientUrl;
    }

    public String getPixivApi() {
        return pixivApi;
    }

    public void setPixivApi(String pixivApi) {
        this.pixivApi = pixivApi;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
