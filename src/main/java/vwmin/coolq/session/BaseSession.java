package vwmin.coolq.session;


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

    Long source_id;
    String message_type;
    String[] args;

    private boolean isOver = false;

    void close(){
        this.isOver = true;
    }

    void reset(Long source_id, String message_type, String[] args){
        this.source_id = source_id;
        this.message_type = message_type;
        this.args = args;
    }

    abstract public void update(Long source_id, String message_type, String[] args);

}



