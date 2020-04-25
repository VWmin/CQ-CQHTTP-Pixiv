package com.vwmin.coolq.honkar3rd.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/22 21:53
 */
@Getter
@Setter
public class Equipment extends Item implements Cloneable{
    private int maxRarity;
    private int lv = 1;

    @Override
    public Item clone() {
        Equipment clone = (Equipment) super.clone();
        clone.setMaxRarity(this.maxRarity);
        return clone;
    }
}
