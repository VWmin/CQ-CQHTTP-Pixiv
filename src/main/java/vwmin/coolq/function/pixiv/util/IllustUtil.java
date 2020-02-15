package vwmin.coolq.function.pixiv.util;

import vwmin.coolq.function.pixiv.entity.ListIllustResponse;

public class IllustUtil {
    static String getMetaSinglePage(ListIllustResponse.IllustsBean illust){
        if(illust.getPage_count() > 1){
            return illust.getMeta_pages().get(0).getImage_urls().getOriginal();
        }else{
            return illust.getMeta_single_page().getOriginal_image_url();
        }
    }

    static String getImgType(ListIllustResponse.IllustsBean illust){
        if(illust.getPage_count() > 1){
            String str = illust.getMeta_pages().get(0).getImage_urls().getOriginal();
            return str.substring(str.length()-4);
        }else{
            String str = illust.getMeta_single_page().getOriginal_image_url();
            return str.substring(str.length()-4);
        }
    }
}
