package vwmin.coolq.session;


import vwmin.coolq.entity.SendMessageEntity;
import vwmin.coolq.enums.ArgsDispatcherType;
import vwmin.coolq.service.BaseService;
import vwmin.coolq.util.BaseConsumer;

/**
 * 如何设计这个session？
 * 为什么想要加入session，因为有的动作存在一系列前后关联的步骤，如果不保存用户对一个动作进行的状态，那么不可能将动作的前后步骤串起来
 * 首先，我觉得每个session都应该有一个唯一的名称来标识和维护当前正在进行的动作
 * 生命周期：session在一个用户启动步骤时创建，步骤完成时结束，或是试图进入其他步骤时，替换成其他类型的session
 * 基本内容：会话发起者user_id；会话从何处发起（私聊、讨论组、群组、陌生人）HasID.getId()；session名；
 * 特殊内容：不同的动作由不同的步骤组成，特殊内容记录这些步骤的状态；session持有的数据
 * 上文：收到一个新的命令后，根据user_id从session map中查找对应的会话
 *      if find：查看新命令动作是否与当前动作相同；如果相同，查看当前步骤是否是上一步骤的后继？如果是，执行，刷新session；如果不是，
 *          重置session；如果不同，重置session
 *      else：创建session置入map
 *
 * 关于session、dispatcher之间的关系：命令接收后，首次无session，那么创建session后，由dispatcher保持session引用并添加数据
 *
 * 下文（使用）：没有session的情况下，被创建的session如何使用？有session的情况下，存在的session如何使用？
 *             没有：
 *             有：
 * 下文（消除）：不再需要的session怎么把自己从map中除掉？给自己添加标记，每次处理命令之后，去掉已确认关闭的session
 */
public abstract class BaseSession {

    Long user_id;
    Long source_id;
    String message_type;
    String[] args;

    Step before;
    BaseConsumer consumer;

    private boolean isOver = false;


    public BaseSession(Long user_id, Long source_id, String message_type, String[] args){
        this.user_id = user_id;
        this.source_id = source_id;
        this.message_type = message_type;
        this.args = args;
    }

    void close(){
        this.isOver = true;
    }

    public boolean isClosed(){
        return isOver;
    }

    void reset(Long source_id, String message_type, String[] args){
        this.reset();
        this.source_id = source_id;
        this.message_type = message_type;
        this.args = args;
    }

    private void reset(String[] args){
        this.reset();
        this.args = args;
    }

    private void reset(){
        before = null;
        consumer = null;
    }

    private void update(Long source_id, String message_type){
        this.source_id = source_id;
        this.message_type = message_type;
    }

    void commonUpdate(Long source_id, String message_type, String[] args, Step current){
        //如果消息来源换了，只更新来源和类型，不重置
        if(!source_id.equals(this.source_id) || !message_type.equals(this.message_type)){
            this.update(source_id, message_type);
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

    abstract public void update(Long source_id, String message_type, String[] args);

    abstract public SendMessageEntity checkAndExecute(BaseService baseService);

    abstract public ArgsDispatcherType getBelong();

}



