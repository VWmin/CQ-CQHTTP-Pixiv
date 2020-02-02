package vwmin.coolq.session;

import org.apache.commons.cli.*;
import vwmin.coolq.entity.HasId;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.service.PixivService;
import vwmin.coolq.function.pixiv.util.IllustsResponseConsumer;
import vwmin.coolq.service.BaseService;
import vwmin.coolq.util.ErrorConsumer;

public class WordSession extends BaseSession {
    private static Options options = null;
    private static CommandLineParser parser = new DefaultParser();

    private static final Step STEP_1 = new Step(1, "search_word", false);
    private static final Step STEP_2 = new Step(2, "get_next", true);


    public WordSession(Long user_id, Long source_id, String message_type, String[] args) {
        super(user_id, source_id, message_type, args);
        if(options == null){
            options = new Options();
            options.addOption("u", true, "指定搜索多少收藏以上");
            options.addOption("s", true, "指定结果排序规则"); //desc aes
            options.addOption("t", true, "指定关键词匹配规则"); //exact part both
            options.addOption("d", "结果中是否显示图片？");
        }
    }

    @Override
    public void update(Long source_id, String message_type, String[] args) {
        if(before == null){
            before = STEP_1.clone();
            this.args = args;
        }else{
            final Step current;
            if(args[0].equals("word")) current = STEP_1.clone();
            else if(args[0].equals("next")) current = STEP_2.clone();
            else return;

            commonUpdate(source_id, message_type, args, current);

        }
    }

    @Override
    public SendMessageEntity checkAndExecute(BaseService pixivService) {
        boolean isNext = false;
        String bookmarks = null;
        String sort = "date_desc";
        String search_target = "partial_match_for_tags";
        String word = "";
        boolean isShowDetail = false;
        try {
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("d")){
                isShowDetail = true;
            }

            if(args[0].equals("next")){
                isNext = true;
            }else if(args[0].equals("word")){
                word = args[args.length-1];
                if(args.length == 1){
                    throw new ParseException("word命令未指定任何参数");
                }
                if(cmd.hasOption("u")){
                    bookmarks = cmd.getOptionValue("u");
                    word += " "+bookmarks+"users入り";
                }
                if(cmd.hasOption("s")){
                    sort = cmd.getOptionValue("s");
                }
                if(cmd.hasOption("t")){
                    search_target = cmd.getOptionValue("t");
                }
            }

        } catch (ParseException e) { //命令行解析错误
            return new SendMessageEntity(message_type, source_id, new ErrorConsumer(user_id, e.getMessage()).response());
        }

        SendMessageEntity send;
        if(!isNext){
            ListIllustResponse illustsResponse = ((PixivService)pixivService).getIllustByWord(word, sort, search_target);
            consumer = new IllustsResponseConsumer(illustsResponse, user_id, "none");
            send = new SendMessageEntity(message_type, source_id, ((IllustsResponseConsumer)consumer).top10(isShowDetail));
        }else{
            if(((IllustsResponseConsumer)consumer).isNeedRefresh()){
                String nextUrl = ((IllustsResponseConsumer)consumer).getNextUrl();
                ListIllustResponse illustResponse = ((PixivService)pixivService).getNext(nextUrl);
                ((IllustsResponseConsumer)consumer).setResponse(illustResponse);
                ((IllustsResponseConsumer)consumer).setNeedRefresh(false);

            }
            send = new SendMessageEntity(message_type, source_id, ((IllustsResponseConsumer)consumer).top10(isShowDetail));
        }

        return send;
    }

    @Override
    public ArgsDispatcherType getBelong() {
        return ArgsDispatcherType.PIXIV;
    }

}
