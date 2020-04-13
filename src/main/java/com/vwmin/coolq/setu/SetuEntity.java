package com.vwmin.coolq.setu;

import lombok.Data;

import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 16:23
 */
@Data
public class SetuEntity {

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;

    @Data
    public static class DataBean {
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
