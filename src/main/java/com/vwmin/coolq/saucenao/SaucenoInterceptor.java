package com.vwmin.coolq.saucenao;

import com.vwmin.coolq.common.Utils;
import com.vwmin.terminalservice.CQcodeExtracter;
import com.vwmin.terminalservice.CommandInterceptor;
import com.vwmin.terminalservice.entity.CQcodeEntity;
import com.vwmin.terminalservice.entity.PostEntity;
import org.springframework.stereotype.Component;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 11:43
 */
@Component
public class SaucenoInterceptor implements CommandInterceptor {
    @Override
    public boolean isMatch(PostEntity postEntity) {
        return postEntity.getRaw_message().startsWith("search");
    }

    @Override
    public String handle(String raw) {
        CQcodeEntity cqCode = CQcodeExtracter.parse(raw);
        Utils.notNull(cqCode, "未检测到图片");
        return "search " +
                cqCode.getData().get("url");
    }
}
