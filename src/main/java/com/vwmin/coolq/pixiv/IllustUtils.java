package com.vwmin.coolq.pixiv;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 13:21
 */
public class IllustUtils {
    private IllustUtils(){}

    public static String getMetaSinglePage(Illusts.IllustsBean illust){
        if(illust.getPage_count() > 1){
            return illust.getMeta_pages().get(0).getImage_urls().getOriginal();
        }else{
            return illust.getMeta_single_page().getOriginal_image_url();
        }
    }

    public static String getImgType(Illusts.IllustsBean illust){
        if(illust.getPage_count() > 1){
            String str = illust.getMeta_pages().get(0).getImage_urls().getOriginal();
            return str.substring(str.length()-4);
        }else{
            String str = illust.getMeta_single_page().getOriginal_image_url();
            return str.substring(str.length()-4);
        }
    }
}
