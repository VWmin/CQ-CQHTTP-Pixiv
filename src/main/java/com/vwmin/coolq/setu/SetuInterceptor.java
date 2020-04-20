package com.vwmin.coolq.setu;

import com.vwmin.terminalservice.CommandInterceptor;
import com.vwmin.terminalservice.entity.PostEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 12:58
 */
@Component
public class SetuInterceptor implements CommandInterceptor {

    private final Pattern REGEX = Pattern.compile(".*([一张色图]).*([一张色图]).*([一张色图]).*([一张色图]).*");

    @Override
    public String handle(String raw) {
        return REGEX.matcher(raw).replaceAll("setu");
    }

    @Override
    public boolean isMatch(PostEntity postEntity) {
        return  REGEX.matcher(postEntity.getMessage()).matches() ;
    }
}
