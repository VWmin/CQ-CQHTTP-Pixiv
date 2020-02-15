package vwmin.coolq.function.saucenao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vwmin.coolq.function.saucenao.entity.SaucenaoEntity;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.calladapter.ObservableCallAdapterFactory;
import vwmin.coolq.network.converter.GsonConverterFactory;

import java.io.IOException;


@Slf4j
@Service
public class SaucenaoServiceImpl implements SaucenaoService{
    private static final String SAUCENAO_BASE_URL = "http://saucenao.com";
    private final SauceNAO sauceNAO;

    SaucenaoServiceImpl(){
        NetworkClient<SauceNAO> client = new NetworkClient<>(SAUCENAO_BASE_URL, SauceNAO.class,
                ObservableCallAdapterFactory.create(), GsonConverterFactory.create());
        sauceNAO = client.getApi();
    }

    @Override
    public SaucenaoEntity getSearchResponse(String url, Integer db) throws IOException{
        return sauceNAO.search(url, db, 2, 3).result();
    }
}
