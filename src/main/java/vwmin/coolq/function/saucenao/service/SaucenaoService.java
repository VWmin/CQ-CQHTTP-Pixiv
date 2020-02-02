package vwmin.coolq.function.saucenao.service;

import vwmin.coolq.function.saucenao.entity.SauceNAOEntity;
import vwmin.coolq.service.BaseService;

public interface SaucenaoService extends BaseService {

    SauceNAOEntity getSearchResponse(String url, Integer db);
}
