package vwmin.coolq.function.setu.service;

import com.vwmin.restproxy.RestProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vwmin.coolq.function.setu.entity.SetuEntity;
import vwmin.coolq.function.setu.entity.SetuCommandParam;

@Service
public class SetuServiceImpl implements SetuService {
    private final static String BASE_URL = "https://api.lolicon.app";
    private final SetuApi setuApi;

    public SetuServiceImpl(@Qualifier("normalRestTemplate")RestTemplate restTemplate){
        this.setuApi = new RestProxy<>(BASE_URL, SetuApi.class, restTemplate).getApi();
    }

    @Override
    public SetuEntity setu(SetuCommandParam... parameters) {
        String r18 = null;
        String keyword = null;
        String num = null;
        String proxy = null;
        String size1200 = null;
        for (SetuCommandParam parameter:parameters){
            switch (parameter.name()){
                case "r18":
                    r18 = parameter.value();
                    break;
                case "keyword":
                    keyword = parameter.value();
                    break;
                case "num":
                    num = parameter.value();
                    break;
                case "proxy":
                    proxy = parameter.value();
                    break;
                case "size1200":
                    size1200 = parameter.value();
                    break;
                default:
                    break;
            }
        }
        return setuApi.setu(r18, keyword, num, proxy, size1200);
    }
}
