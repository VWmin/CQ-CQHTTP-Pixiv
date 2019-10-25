package vwmin.coolq.function.saucenao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vwmin.coolq.function.saucenao.entity.SauceNAOEntity;
import vwmin.coolq.network.MyCallAdapterFactory;
import vwmin.coolq.network.NetworkClient;
import vwmin.coolq.network.Response;

@Slf4j
@Service
public class SaucenaoServiceImpl implements SaucenaoService{
    private static final String SAUCENAO_BASEURL = "http://saucenao.com";
    private final SauceNAO sauceNAO;

    SaucenaoServiceImpl(){
        NetworkClient<SauceNAO> client = new NetworkClient<>(SAUCENAO_BASEURL, SauceNAO.class, MyCallAdapterFactory.create());
        sauceNAO = client.getApi();
    }

    @Override
    public SauceNAOEntity getSearchResponse(String url, Integer db) {
        Response<SauceNAOEntity> sauceNAOResponse = sauceNAO.search(url, db, 2, 3);
        return sauceNAOResponse.getResponse();
    }
}
