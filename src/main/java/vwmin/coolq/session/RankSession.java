package vwmin.coolq.session;

import org.apache.commons.cli.*;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.function.pixiv.util.IllustsResponseConsumer;
import vwmin.coolq.service.BaseService;
import vwmin.coolq.util.ErrorConsumer;


public class RankSession extends BaseSession{

    //关于添加时机，session将在controller中立即创建，或是由dispatcher保持一个mao引用，
    // 到dispatcher分发命令后加入session。
    // 但是此时数据还未添加进来，因此consumer应在数据请求后添加，或是从上次命令继承而来


    private static Options options = null;
    private static CommandLineParser parser = new DefaultParser();

    private static final Step STEP_1 = new Step(1, "get_rank", false);
    private static final Step STEP_2 = new Step(2, "get_next", true);



    private final String[] RANK_MODE_LIST = {"day", "week", "month", "day_male", "day_female", "week_original",
            "week_rookie", "day_manga", "day_r18", "day_male_r18", "day_female_r18", "week_r18", "week_r18g"};

    public RankSession(Long user_id, Long source_id, String message_type, String[] args){
        super(user_id, source_id, message_type, args);
        if(options == null){
            options = new Options();
//            options.addOption("n", "可选，存在时不解析其他参数，");
            options.addOption("d", true, "可选，接收yyyy-MM-dd格式日期，指定特定日期的排行");
            options.addOption("s", "可选，指定参数时最简化显示，即不显示图片");
        }
    }

    /**
     * 当有已存在的session时，调用此函数进行session更新
     * @param source_id
     * @param message_type
     * @param args
     */
    @Override
    public void update(Long source_id, String message_type, String[] args){
        if(before == null){ //无冲突
            before = STEP_1.clone();
            this.args = args;
        }else{ //可能有冲突
            final Step current;
            if(args[0].equals("rank")) current = STEP_1.clone();
            else if(args[0].equals("next")) current = STEP_2.clone();
            else return; //不可能出现啊...

            commonUpdate(source_id, message_type, args, current);

        }
    }


    @Override
    public SendMessageEntity checkAndExecute(BaseService pixivService){
        //检查命令

        boolean isNext = false; //显示下一页?
        boolean isSmall = false; //最小化显示?
        String mode = RANK_MODE_LIST[0];
        String date = null;
        try {
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("s")){
                isSmall = true;
            }

            if(args[0].equals("next")){
                isNext = true;

            }else if(args[0].equals("rank")){
                if(args.length == 1){
                    throw new ParseException("rank命令未指定任何参数");
                }
                if(cmd.hasOption("d")){
                    date = cmd.getOptionValue("d");
                }
                if(isARankMode(args[args.length-1])) {
                    mode = args[args.length-1];
                }
            }
        } catch (ParseException e) {
            String cause = e.getMessage();
            return new SendMessageEntity(message_type, source_id, new ErrorConsumer(user_id, cause).response());
        }

        //执行命令
        SendMessageEntity send;

        if(!isNext){
            ListIllustResponse rankResponse = ((PixivService)pixivService).getRank(mode, date);
            consumer = new IllustsResponseConsumer(rankResponse, user_id, mode);
            send = new SendMessageEntity(message_type, source_id, ((IllustsResponseConsumer)consumer).top10(!isSmall));
        }else{
            send = new SendMessageEntity(message_type, source_id, ((IllustsResponseConsumer)consumer).top10(!isSmall));
        }

        return send;
    }

    @Override
    public ArgsDispatcherType getBelong() {
        return ArgsDispatcherType.PIXIV;
    }

    private boolean isARankMode(String arg){
        for(String mode : RANK_MODE_LIST){
            if(mode.equals(arg))
                return true;
        }
        return false;
    }


}
