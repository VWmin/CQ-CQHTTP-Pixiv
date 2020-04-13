package com.vwmin.coolq.saucenao;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 10:14
 */
@CommandLine.Command(name = "search", description = "搜图")
public class SaucenaoCommand implements Callable<SaucenaoEntity> {

    @CommandLine.Parameters(index = "0")
    private String url;

    private final
    SaucenaoApi api;

    public SaucenaoCommand(SaucenaoApi api){
        this.api = api;
    }

    @Override
    public SaucenaoEntity call() throws Exception {
        return api.search(url, 999, 2, 3);
    }
}
