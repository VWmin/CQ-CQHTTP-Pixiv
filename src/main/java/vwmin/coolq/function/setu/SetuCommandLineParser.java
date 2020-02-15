package vwmin.coolq.function.setu;

import org.apache.commons.cli.*;
import vwmin.coolq.function.setu.entity.SetuCommandParam;

import java.util.ArrayList;
import java.util.List;

public class SetuCommandLineParser {

    private final CommandLineParser parser = new DefaultParser();

    private final Options options;



    private SetuCommandLineParser(){
        options = new Options();
        options.addOption("r", true, "可选，指定r18等级");
        options.addOption("n", true, "可选，指定一次返回图片数");
    }

    public List<SetuCommandParam> parse(String[] args) throws ParseException {
        CommandLine cmd = parser.parse(options, args);
        List<SetuCommandParam> parameters = new ArrayList<>();
        if (cmd.hasOption("r")){
            String choice = cmd.getOptionValue("r");
            parameters.add(SetuCommandParam.needR18(SetuCommandParam.R18.ifExist(choice)));
        }

        if (cmd.hasOption("n")){
            String n = cmd.getOptionValue("n");
            Integer num;
            try{
                num = Integer.valueOf(n);
            }catch (IllegalArgumentException e){
                throw new ParseException("-n: 仅接受1--10的整数");
            }
            parameters.add(SetuCommandParam.num(num));
        }
        return parameters;
    }


    private volatile static SetuCommandLineParser instance;
    public static SetuCommandLineParser getInstance(){
        if (instance == null){
            synchronized (SetuCommandLineParser.class){
                if (instance == null){
                    instance = new SetuCommandLineParser();
                }
            }
        }
        return instance;
    }
}
