package vwmin.coolq.util;


import lombok.extern.slf4j.Slf4j;
import vwmin.coolq.SpringUtil;
import vwmin.coolq.configuration.BotConfig;
import vwmin.coolq.entity.MessageSegment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static vwmin.coolq.util.DownloadUtil.*;


@Slf4j
public class MessageSegmentBuilder {
    private List<MessageSegment> segments;
//    private static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";

    private String CQ_IMAGE_PATH;


    public MessageSegmentBuilder(){
        this.segments = new ArrayList<>();
        BotConfig botConfig =  (BotConfig) SpringUtil.getBean("botConfig");
        CQ_IMAGE_PATH = botConfig != null ? botConfig.getImagePath() : "/home/ubuntu/coolq/data/image/";
    }

    /**
     * 发送纯文本消息
     * @param text 要发送的文本
     * @return builder实例自身
     */
    public MessageSegmentBuilder addTextSegment(String text){
        log.info("构建文本消息 >> " + text);
        MessageSegment segment = new MessageSegment();
        segment.setType("text");
        segment.addData("text", text);

        segments.add(segment);

        return this;
    }

    /**
     * 发送网路图片
     * @param fileName 图片名
     * @param url 连接
     * @return builder实例自身
     */
    public MessageSegmentBuilder addImageSegment(String fileName, String url) throws IOException {
        if(fileName == null || "".equals(fileName)) {
            log.warn("保存文件名不能为空，本段消息将取消发送");
            return this;
        }

        log.info("构建图片消息 >> name: " + fileName + " url: " + url);
        MessageSegment segment = new MessageSegment();
        segment.setType("image");

        //如果本地没有则下载
        if(!tryLocal(CQ_IMAGE_PATH, fileName)){
            //如果下载失败则取消
            if(!downloadPixivImage(CQ_IMAGE_PATH, fileName, url)) {
                return this;
            }
        }

        segment.addData("file", fileName);
        segments.add(segment);

        return this;
    }

    /**
     * 发送系统表情
     * @param id 表情id
     * @return builder实例自身
     */
    public MessageSegmentBuilder addFaceSegment(Integer id){
        if(id < 1){
            return this;
        }
        MessageSegment segment = new MessageSegment();
        segment.setType("face");
        segment.addData("id", id.toString());

        segments.add(segment);

        return this;
    }

    /**
     * at某个QQ
     * @param user_id QQ号
     * @return 实例自身
     */
    public MessageSegmentBuilder addAtSegment(Long user_id){
        MessageSegment segment = new MessageSegment();
        segment.setType("at");
        segment.addData("qq", user_id.toString());

        segments.add(segment);

        return this;
    }

    /**
     * at 全体成员
     * @return 实例自身
     */
    public MessageSegmentBuilder addAtAllSegment(){
        MessageSegment segment = new MessageSegment();
        segment.setType("at");
        segment.addData("qq", "all");

        segments.add(segment);

        return this;
    }

    /**
     * 发送连接分享
     * @param url 连接地址
     * @param title 标题 建议12字以内
     * @param content 分享简介 建议30字以内 可为空
     * @param image 图片连接 可为空
     * @return 实例自身
     */
    public MessageSegmentBuilder addLinkShareSegment(String url, String title, String content, String image){
        MessageSegment segment = new MessageSegment();
        segment.setType("share");
        segment.addData("url", url);
        segment.addData("title", title);
        segment.addData("content", content);
        segment.addData("image", image);

        segments.add(segment);

        return this;
    }

    public List<MessageSegment> build(){
        return segments;
    }

}
