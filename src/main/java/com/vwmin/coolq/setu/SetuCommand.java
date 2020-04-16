package com.vwmin.coolq.setu;

import com.vwmin.coolq.conf.BotConfig;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 16:22
 */
@CommandLine.Command(name = "setu", description = "给你爱看的")
public class SetuCommand implements Callable<SetuEntity> {

    private final SetuApi setuApi;
    private final String key;

    public SetuCommand(SetuApi setuApi, BotConfig config){
        this.setuApi = setuApi;
        this.key = config.getSetuKey();
    }


    @CommandLine.Option(names = {"-r", "--r18"}, description = "指定r18等级；0: 无, 1: 有, 2：混合")
    private int r18;

    @CommandLine.Option(names = {"-w", "--word"}, description = "指定关键词")
    private String word;

    @Override
    public SetuEntity call() {
        return setuApi.setu(r18, word, 1, null, null, key);
    }
}
