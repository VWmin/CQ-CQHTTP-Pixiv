package vwmin.coolq.function.setu.service;

import org.springframework.stereotype.Service;
import vwmin.coolq.function.setu.entity.SetuEntity;
import vwmin.coolq.function.setu.entity.SetuCommandParam;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.calladapter.ObservableCallAdapterFactory;
import vwmin.coolq.network.converter.GsonConverterFactory;

import java.io.IOException;

@Service
public class SetuServiceImpl implements SetuService {
    private final static String BASE_URL = "https://api.lolicon.app";
    private final SetuApi setuApi;

    public SetuServiceImpl(){
        NetworkClient<SetuApi> client = new NetworkClient<>(BASE_URL, SetuApi.class,
                ObservableCallAdapterFactory.create(), GsonConverterFactory.create());
        this.setuApi = client.getApi();
    }

    @Override
    public SetuEntity setu(SetuCommandParam... parameters) throws IOException {
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
        return setuApi.setu(r18, keyword, num, proxy, size1200).result();
    }
}
