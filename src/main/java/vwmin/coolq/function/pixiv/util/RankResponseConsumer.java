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
public class RankResponseConsumer {
    private final String type;
    private final ListIllustResponse response;

    public RankResponseConsumer(String rankType, ListIllustResponse response){
        this.type = rankType;
        this.response = response;
    }



    public List<MessageSegment> top10(){
        MessageSegmentBuilder builder = new MessageSegmentBuilder();
        if(response == null || response.getIllusts() == null){
            builder.addTextSegment("网络开小差了嗷 等会");
            return builder.build();
        }
        List<ListIllustResponse.IllustsBean> illusts = response.getIllusts();

        builder.addTextSegment("当前排行榜类型："+type+"\n");
        int cnt=0;
        for(int i=0; i<illusts.size(); i++){
            ListIllustResponse.IllustsBean illust = illusts.get(i);
            if(illust.getType().equals("illust")){
                cnt++;
                builder.addTextSegment(cnt+". "+illust.getTitle() + "  view: " + illust.getTotal_view() + " like: " + illust.getTotal_bookmarks() + "\n")
                        .addImageSegment(illust.getId()+getImgType(illust), getMetaSinglePage(illust));
//                        .addTextSegment("\n");
            }
            if(cnt == 10) break;
        }

        return builder.build();
    }




}
