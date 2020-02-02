package vwmin.coolq.function.setu.entity;

import lombok.Data;

import java.util.List;

@Data
public class SetuEntity {

    /**
     * code : 0
     * msg :
     * count : 1
     * data : [{"pid":62693234,"p":2,"uid":3439325,"title":"アイカツ！まとめ","author":"かにビーム","url":"https://i.pixiv.cat/img-original/img/2017/05/02/14/55/37/62693234_p2.jpg","r18":false,"width":1080,"height":886,"tags":["アイカツ!","偶像活动！","アイカツスターズ!","Aikatsu Stars!","霧矢あおい","Aoi Kiriya","白銀リリィ","Lily Shirogane","新条ひなき","Hinaki Shinjo","藤堂ユリカ","Yurika Todo","二階堂ゆず","Yuzu Nikaido","白生","白ハイソックス","白筒袜","アイカツ5000users入り"]}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;



    @Data
    public static class DataBean {
        /**
         * pid : 62693234
         * p : 2
         * uid : 3439325
         * title : アイカツ！まとめ
         * author : かにビーム
         * url : https://i.pixiv.cat/img-original/img/2017/05/02/14/55/37/62693234_p2.jpg
         * r18 : false
         * width : 1080
         * height : 886
         * tags : ["アイカツ!","偶像活动！","アイカツスターズ!","Aikatsu Stars!","霧矢あおい","Aoi Kiriya","白銀リリィ","Lily Shirogane","新条ひなき","Hinaki Shinjo","藤堂ユリカ","Yurika Todo","二階堂ゆず","Yuzu Nikaido","白生","白ハイソックス","白筒袜","アイカツ5000users入り"]
         */

        private int pid;
        private int p;
        private int uid;
        private String title;
        private String author;
        private String url;
        private boolean r18;
        private int width;
        private int height;
        private List<String> tags;
    }
}
