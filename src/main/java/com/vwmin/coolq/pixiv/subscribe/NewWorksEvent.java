package com.vwmin.coolq.pixiv.subscribe;

import com.vwmin.coolq.pixiv.entities.Illust;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/17 0:03
 */
public class NewWorksEvent extends ApplicationEvent {

    private final Long userId;
    private final List<Illust> illusts;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public NewWorksEvent(Object source, Long userId, List<Illust> illusts) {
        super(source);
        this.userId = userId;
        this.illusts = illusts;
    }

    public List<Illust> getIllusts() {
        return illusts;
    }

    public Long getUserId() {
        return userId;
    }
}
