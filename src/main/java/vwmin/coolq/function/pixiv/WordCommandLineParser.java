package vwmin.coolq.function.pixiv;

import org.apache.commons.cli.*;
import vwmin.coolq.function.pixiv.entity.WordCommandParam;
import vwmin.coolq.function.pixiv.entity.WordCommandParam.SortByDate;
import vwmin.coolq.function.pixiv.entity.WordCommandParam.SearchTarget;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordCommandLineParser {
    private final CommandLineParser parser = new DefaultParser();
    private final Options options;

    public WordCommandLineParser(){
        options = new Options();
        options.addOption("u", "users", true, "指定搜索多少收藏以上");
        options.addOption("s", "sort", true, "指定结果排序规则");
        options.addOption("t", "target", true, "指定关键词匹配规则");
    }

    public List<WordCommandParam> parse(String[] args) throws ParseException {
        // FIXME: 2020/2/6 未检查输入
        if (args.length == 1){
            throw new ParseException("word命令未指定任何参数");
        }

        List<WordCommandParam> params = new ArrayList<>();
        String word = args[args.length-1];
        args = Arrays.copyOfRange(args, 0, args.length-1);

        CommandLine cmd = parser.parse(options, args);

        if(cmd.hasOption("u")){
            params.add(WordCommandParam.word(word +" "+ cmd.getOptionValue("u") +"users入り"));
        }

        if(cmd.hasOption("s")){
            params.add(WordCommandParam.sort(SortByDate.ifExist(cmd.getOptionValue("s"))));
        }

        if(cmd.hasOption("t")){
            params.add(WordCommandParam.target(SearchTarget.ifExist(cmd.getOptionValue("t"))));
        }



        return params;
    }

    private volatile static WordCommandLineParser instance;
    public static WordCommandLineParser getInstance(){
        if (instance == null){
            synchronized (WordCommandLineParser.class){
                if (instance == null){
                    instance = new WordCommandLineParser();
                }
            }
        }
        return instance;
    }
}
