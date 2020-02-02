package vwmin.coolq.session;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import vwmin.coolq.entity.HasId;
import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.function.saucenao.entity.SauceNAOEntity;
import vwmin.coolq.function.saucenao.service.SaucenaoService;
import vwmin.coolq.function.saucenao.util.SaucenaoConsumer;
import vwmin.coolq.service.BaseService;
import vwmin.coolq.util.ErrorConsumer;

import java.util.Arrays;

public class SearchSession extends BaseSession {

    private static Options options = null;
    private static CommandLineParser parser = new DefaultParser();

    private static final Step STEP_1 = new Step(1, "announcement", false);
    private static final Step STEP_2 = new Step(2, "tackle", true);

    public SearchSession(Long user_id, Long source_id, String message_type, String[] args) {
        super(user_id, source_id, message_type, args);
        if(options == null){
            options = new Options();
//            options.addOption();
        }
    }

    @Override
    public void update(Long source_id, String message_type, String[] args) {
        super.reset(source_id, message_type, args);
    }

    @Override
    public SendMessageEntity checkAndExecute(BaseService saucenaoService) {
        System.out.println(Arrays.toString(args));
        SendMessageEntity send;
        if(args[0].contains("search")){
            send = null;
        }else{
            String imageUrl = args[args.length-1];
            SauceNAOEntity searchResponse = ((SaucenaoService) saucenaoService).getSearchResponse(imageUrl, 999);
            if(searchResponse != null && searchResponse.getResults().size()>0){
                SaucenaoConsumer consumer = new SaucenaoConsumer(searchResponse, user_id);
                send = new SendMessageEntity(message_type, source_id, consumer.mostly());
                close();
            }else{
                try {
                    throw new Exception("没有返回任何结果");
                } catch (Exception e) {
                    String cause = e.getMessage();
                    send = new SendMessageEntity(message_type, source_id, new ErrorConsumer(user_id, cause).response());
                }
            }
        }


        return send;
    }

    @Override
    public ArgsDispatcherType getBelong() {
        return ArgsDispatcherType.SAUCENAO;
    }
}
