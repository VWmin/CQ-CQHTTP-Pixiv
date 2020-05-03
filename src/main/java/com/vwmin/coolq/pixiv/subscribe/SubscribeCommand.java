package com.vwmin.coolq.pixiv.subscribe;

import com.vwmin.coolq.common.RedisUtil;
import com.vwmin.coolq.pixiv.PixivApi;
import picocli.CommandLine;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/5/3 16:03
 */
@CommandLine.Command(description = "订阅pixiv画师新作")
public class SubscribeCommand {
    private static final String CACHE_NAME = "newWorks:";
    private static final String SUBSCRIBERS_KEY = "_subscribers";


    @CommandLine.Option(names = {"-u", "--user"}, required = true)
    String username;

    @CommandLine.Option(names = {"-p", "--pawd"}, required = true)
    String password;

}
