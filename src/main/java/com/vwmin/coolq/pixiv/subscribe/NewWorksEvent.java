package com.vwmin.coolq.pixiv.subscribe;

import com.vwmin.coolq.pixiv.Illust;
import com.vwmin.coolq.pixiv.Illusts;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/17 0:03
 */
public class NewWorksEvent extends ApplicationEvent {

    private final List<Illust> illusts;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public NewWorksEvent(Object source, List<Illust> illusts) {
        super(source);
        this.illusts = illusts;
    }

    public List<Illust> getIllusts() {
        return illusts;
    }
}
