package vwmin.coolq.function.pixiv.util;

import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.util.MessageSegmentBuilder;

import java.util.List;

import static vwmin.coolq.function.pixiv.util.IllustUtil.getImgType;
import static vwmin.coolq.function.pixiv.util.IllustUtil.getMetaSinglePage;

/**
 * 处理收到的Response内容 返回发送消息所用的数据段
 */
public class IllustsResponseConsumer {

    private final ListIllustResponse response;
    private final Long user_id;
    private int offset = 0;
    public IllustsResponseConsumer(ListIllustResponse response, Long user_id){
        this.response = response;
        this.user_id = user_id;
    }



    public List<MessageSegment> top10(String rankType, boolean isShowDetail){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        if(response == null || response.getIllusts() == null){
            builder.addTextSegment("看样子没找到任何结果呢 等会再试试看？");
            return builder.build();
        }
        List<ListIllustResponse.IllustsBean> illusts = response.getIllusts();

        builder.addAtSegment(user_id).addTextSegment("\n");
        if(!"none".equals(rankType)) builder.addTextSegment("当前排行榜类型："+rankType+"\n");
        int cnt=0;
        for(int i=0; i<illusts.size(); i++){
            ListIllustResponse.IllustsBean illust = illusts.get(i);
            if(illust.getType().equals("illust")){
                cnt++;
                builder.addTextSegment(cnt+". "+illust.getTitle() + "  view: " + illust.getTotal_view() + " like: " + illust.getTotal_bookmarks() + "\n");
                if(isShowDetail) builder.addImageSegment(illust.getId()+getImgType(illust), getMetaSinglePage(illust)).addTextSegment("\n");
                else builder.addTextSegment("\n");
            }
            if(cnt == 10) break;
        }
        offset+=10;
        //todo: 当offset大于30时，应当请求next_url
        return builder.build();
    }




}
