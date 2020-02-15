package vwmin.coolq.function.pixiv;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import vwmin.coolq.function.pixiv.entity.RankCommandParam;

import java.util.ArrayList;
import java.util.List;

public class RankCommandLineParser {
    private final CommandLineParser parser = new DefaultParser();
    private final Options options;

    public RankCommandLineParser(){
        options = new Options();
//        options.addOption("d", true, "可选，接收yyyy-MM-dd格式日期，指定特定日期的排行");
//        options.addOption("s", "可选，指定参数时最简化显示，即不显示图片");
    }

    public List<RankCommandParam> parse(String[] args) throws ParseException {
        if (args.length == 1){
            throw new ParseException("rank命令未指定任何参数");
        }

        List<RankCommandParam> parameters = new ArrayList<>();

        parameters.add(RankCommandParam.rankMode(RankCommandParam.RankMode.ifExist(args[args.length - 1])));
        return parameters;
    }


    private volatile static RankCommandLineParser instance;
    public static RankCommandLineParser getInstance(){
        if (instance == null){
            synchronized (RankCommandLineParser.class){
                if (instance == null){
                    instance = new RankCommandLineParser();
                }
            }
        }
        return instance;
    }
}
