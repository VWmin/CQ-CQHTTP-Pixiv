package vwmin.coolq.function.saucenao;

import vwmin.coolq.function.Command;
import vwmin.coolq.function.saucenao.entity.SaucenaoEntity;
import vwmin.coolq.function.saucenao.service.SaucenaoService;

import java.io.IOException;

public class SaucenaoCommand implements Command<SaucenaoEntity> {

    private SaucenaoService saucenaoService;
    private String imgUrl;

    public SaucenaoCommand(SaucenaoService saucenaoService, String imgUrl){
        this.saucenaoService = saucenaoService;
        this.imgUrl = imgUrl;
    }



    @Override
    public SaucenaoEntity execute() throws IOException {
        return saucenaoService.getSearchResponse(imgUrl, 999);
    }
}
