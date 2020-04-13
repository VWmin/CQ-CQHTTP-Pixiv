package com.vwmin.coolq.saucenao;

import com.vwmin.coolq.pixiv.Illust;
import com.vwmin.coolq.pixiv.IllustUtils;
import com.vwmin.coolq.pixiv.Illusts;
import com.vwmin.coolq.pixiv.PixivApi;
import com.vwmin.terminalservice.MessageSegmentBuilder;
import com.vwmin.terminalservice.entity.MessageSegment;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 10:25
 */

@Slf4j
public class SaucenaoConsumer {
    private final SaucenaoEntity sauceNAOEntity;
    private final Long userId;
    private final PixivApi pixivApi;

    public SaucenaoConsumer(SaucenaoEntity saucenaoEntity, Long userId, PixivApi api){
        this.sauceNAOEntity = saucenaoEntity;
        this.userId = userId;
        this.pixivApi = api;
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
        double similarity = Double.parseDouble(one.getHeader().getSimilarity());

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

        // 通用
        String fileName = timeStamp+imageType;
        String fileUrl = one.getHeader().getThumbnail();


        if(one.getHeader().getIndex_id() == 5){ //Pixiv
            Illust illustById = pixivApi.getIllustById(one.getData().getPixiv_id());
            if(illustById!=null && illustById.getIllust()!=null){
                Illusts.IllustsBean illust = illustById.getIllust();
                fileName = illust.getId() + IllustUtils.getImgType(illust);
                fileUrl = IllustUtils.getMetaSinglePage(illust);
            }
        }

        if(similarity < 50){
            builder.plainText("[找到图片相似度过低(" + one.getHeader().getSimilarity() + "%)，结果不给康了嗷，感兴趣请戳↓]\n")
                    .plainText(one.getData().getExt_urls().get(0));
        }else{
            builder.plainText("[找到图片相似度：" + one.getHeader().getSimilarity() + "%]\n")
                    .plainText("[" + title + "]  by  [" + author + "]")
                    .image(fileName, fileUrl);
            if(one.getData().getExt_urls()!=null && one.getData().getExt_urls().size()>0){
                builder.plainText(one.getData().getExt_urls().get(0));
            }
        }

//        if(short_remaining<5){
//            builder.addTextSegment("\nwarn >> 30s内剩余次数："+short_remaining);
//        }
        if(long_remaining < 20){
            builder.plainText("\nwarn >> 24h内剩余次数："+long_remaining);
        }



        return builder.build();
    }
}

