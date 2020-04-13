package com.vwmin.coolq.pixiv;

import com.vwmin.terminalservice.CommandController;
import com.vwmin.terminalservice.Reply;
import com.vwmin.terminalservice.entity.PostEntity;
import com.vwmin.terminalservice.entity.ReplyEntity;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 17:56
 */
@CommandController(bind="rank")
public class RankCommandController implements Reply {


    @Override
    public ReplyEntity call(PostEntity postEntity) {
        return null;
    }
}
