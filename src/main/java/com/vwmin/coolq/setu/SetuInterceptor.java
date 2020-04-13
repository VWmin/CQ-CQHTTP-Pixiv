package com.vwmin.coolq.setu;

import com.vwmin.terminalservice.CommandInterceptor;
import com.vwmin.terminalservice.entity.PostEntity;
import org.springframework.stereotype.Component;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 12:58
 */
@Component
public class SetuInterceptor implements CommandInterceptor {
    @Override
    public String handle(String raw) {
        return raw.replace("一张色图", "setu");
    }

    @Override
    public boolean isMatch(PostEntity postEntity) {
        return postEntity.getMessage().startsWith("一张色图");
    }
}
