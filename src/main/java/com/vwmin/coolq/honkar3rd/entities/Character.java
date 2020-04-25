package com.vwmin.coolq.honkar3rd.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/22 21:48
 */
@Getter
@Setter
public class Character extends Item implements Cloneable{
    private String rank;
    private boolean isDebris;

    @Override
    public Item clone() {
        Character clone = (Character) super.clone();
        clone.setRank(this.getRank());
        return clone;
    }
}
