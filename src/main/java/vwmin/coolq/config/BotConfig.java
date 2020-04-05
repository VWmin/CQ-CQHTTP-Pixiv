package vwmin.coolq.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("botConfig")
@ConfigurationProperties(prefix = "botconfig")
public class BotConfig {
    private String imagePath;
    private String pixivApi;
    private String cqClientUrl;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

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
