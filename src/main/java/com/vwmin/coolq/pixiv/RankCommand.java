package com.vwmin.coolq.pixiv;

import com.vwmin.coolq.pixiv.entities.Illusts;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 18:00
 */
@CommandLine.Command(name = "rank", description = "显示排行榜")
public class RankCommand implements Callable<Illusts> {

    @CommandLine.Parameters(index = "0")
    private RankMode mode;


    private final PixivApi api;
    public RankCommand(PixivApi api){
        this.api = api;
    }

    @Override
    public Illusts call() throws Exception {
        return api.getRank(mode.value);
    }


    public enum RankMode{
        /**模式*/
        day("day"),
        week("week"),
        month("month"),
        day_male("day_male"),
        week_original("week_original"),
        week_rookie("week_rookie"),
        day_r18("day_r18"),
        day_male_r18("day_male_r18"),
        week_r18("week_r18");

        private String value;
        RankMode(String value){
            this.value = value;
        }
    }
}
