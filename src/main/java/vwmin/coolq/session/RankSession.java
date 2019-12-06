package vwmin.coolq.session;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import vwmin.coolq.function.pixiv.util.IllustsResponseConsumer;


public class RankSession extends BaseSession{

    //关于添加时机，session将在controller中立即创建，或是由dispatcher保持一个mao引用，
    // 到dispatcher分发命令后加入session。
    // 但是此时数据还未添加进来，因此consumer应在数据请求后添加，或是从上次命令继承而来
    private IllustsResponseConsumer consumer = null;

    private static Options options = null;
    private static CommandLineParser parser = new DefaultParser();

    private static final Step STEP_1 = new Step(1, "get_rank", false);
    private static final Step STEP_2 = new Step(2, "get_next", true);
    private Step before = null;


    private final String[] RANK_MODE_LIST = {"day", "week", "month", "day_male", "day_female", "week_original",
            "week_rookie", "day_manga", "day_r18", "day_male_r18", "day_female_r18", "week_r18", "week_r18g"};

    public RankSession(Long source_id, String message_type, String[] args){
        this.source_id = source_id;
        this.message_type = message_type;
        this.args = args;

        if(options == null){
            options = new Options();
            options.addOption("d", true, "可选，接收yyyy-MM-dd格式日期，指定特定日期的排行");
        }
    }

    public void check(Long source_id, String message_type, String[] args){
        if(before == null){ //无冲突
            before = STEP_1.clone();
        }else{ //可能有冲突
            final Step current;
            if(args[0].equals("rank")) current = STEP_1.clone();
            else if(args[0].equals("next")) current = STEP_2.clone();
            else return; //不可能出现啊...

            //如果消息来源换了，完全重置session
            if(!source_id.equals(this.source_id) || !message_type.equals(this.message_type)){
                this.reset(source_id, message_type, args);
            }

            if(current.getCurrentStep() == before.getCurrentStep()){
                if(!before.isRepeatable()) { //如果这个步骤与上一个步骤相同，且是不可重复的，重置session
                    this.reset(args);
                }
                else{ //如果这个步骤与上一个步骤相同，但是可重复
                    this.args = args; //只需要更新命令
                }
            } else if(current.getCurrentStep() > before.getCurrentStep()){ //如果这个步骤大于上一个步骤
                this.args = args; //只需要更新命令
            } else{ //如果这个步骤小于上一个步骤，视为重置
                this.reset(args);
            }

        }
    }








    // rank detail user word
    /**
     * rank
     * 1. 显示前10个
     * 2. 显示下一页
     *
     * 结论：维护Consumer
     */
    /**
     * word
     * 1. 显示前10个
     * 2. 显示下一页
     *
     * 同上
     */
    /**
     * detail、user
     * 暂时不做改动
     */
    // detail can't
    // user can't

    public IllustsResponseConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(IllustsResponseConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void reset(Long source_id, String message_type, String[] args){
        this.reset();
        super.reset(source_id, message_type, args);
    }

    public void reset(String[] args){
        this.reset();
        this.args = args;
    }

    public void reset(){
        before = STEP_1.clone();
        consumer = null;
    }

}
