package vwmin.coolq.function.saucenao.util;

import lombok.extern.slf4j.Slf4j;
import vwmin.coolq.entity.MessageSegment;
import vwmin.coolq.function.saucenao.entity.SaucenaoEntity;
import vwmin.coolq.core.MessageSegmentBuilder;

import java.io.IOException;
import java.util.List;

@Slf4j
public class SaucenaoConsumer {
    private final SaucenaoEntity sauceNAOEntity;
    private final Long userId;
//    private final PixivService pixivService;
    public SaucenaoConsumer(SaucenaoEntity saucenaoEntity, Long userId){
        this.sauceNAOEntity = saucenaoEntity;
        this.userId = userId;
//        this.pixivService = pixivService;
    }


    public List<MessageSegment> mostly() throws IOException {
        MessageSegmentBuilder builder = new MessageSegmentBuilder();

        SaucenaoEntity.ResultsBean one = sauceNAOEntity.getResults().get(0);
        SaucenaoEntity.HeaderBean header = sauceNAOEntity.getHeader();

        log.info("SauceNAO the first result  >>  " + one);

        long timeStamp = System.currentTimeMillis();
        String imageType = one.getHeader().getIndex_name().substring(one.getHeader().getIndex_name().lastIndexOf("."));
        int short_remaining = header.getShort_remaining();
        int long_remaining = header.getLong_remaining();
        double similarity = Double.valueOf(one.getHeader().getSimilarity());

        String title = "我是图片名";
        if(one.getData().getTitle()!=null){
            title = one.getData().getTitle();
        }else if(one.getData().getJp_name()!=null){
            title = one.getData().getJp_name();
        }else if(one.getData().getEng_name()!=null){
            title = one.getData().getEng_name();
        }
        String author = "我是作者";
        if(one.getData().getMember_name()!=null) {
            author = one.getData().getMember_name();
        }
//        }else if(one.setData().getCreator()!=null){
//            author = one.setData().getCreator() + "";
//        }

        // 通用
        String fileName = timeStamp+imageType;
        String fileUrl = one.getHeader().getThumbnail();


        if(one.getHeader().getIndex_id() == 5){ //Pixiv
//            IllustResponse illustById = pixivService.getIllustById(one.setData().getPixiv_id());
//            if(illustById!=null && illustById.getIllust()!=null){
//                ListIllustResponse.IllustsBean illust = illustById.getIllust();
//                fileName = illust.getId() + IllustUtil.getImgType(illust);
//                fileUrl = IllustUtil.getMetaSinglePage(illust);
//            }
        }

        if(similarity < 50){
            builder.addAtSegment(userId)
                    .addTextSegment("[找到图片相似度过低(" + one.getHeader().getSimilarity() + "%)，结果不给康了嗷，感兴趣请戳↓]\n")
                    .addTextSegment(one.getData().getExt_urls().get(0));
        }else{
            builder.addAtSegment(userId)
                    .addTextSegment("[找到图片相似度：" + one.getHeader().getSimilarity() + "%]\n")
                    .addTextSegment("[" + title + "]  by  [" + author + "]")
                    .addImageSegment(fileName, fileUrl);
            if(one.getData().getExt_urls()!=null && one.getData().getExt_urls().size()>0){
                builder.addTextSegment(one.getData().getExt_urls().get(0));
            }
        }

//        if(short_remaining<5){
//            builder.addTextSegment("\nwarn >> 30s内剩余次数："+short_remaining);
//        }
        if(long_remaining < 20){
            builder.addTextSegment("\nwarn >> 24h内剩余次数："+long_remaining);
        }



        return builder.build();
    }
}
