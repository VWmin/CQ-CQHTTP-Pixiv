package vwmin.coolq.function.saucenao.service;

import vwmin.coolq.function.saucenao.entity.SauceNAOEntity;

public interface SaucenaoService {

    SauceNAOEntity getSearchResponse(String url, Integer db);
}
