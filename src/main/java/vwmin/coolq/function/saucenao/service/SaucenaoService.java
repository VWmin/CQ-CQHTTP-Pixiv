package vwmin.coolq.function.saucenao.service;

import vwmin.coolq.function.saucenao.entity.SaucenaoEntity;
import vwmin.coolq.service.BaseService;

import java.io.IOException;

public interface SaucenaoService extends BaseService {

    SaucenaoEntity getSearchResponse(String url, Integer db) throws IOException;
}
