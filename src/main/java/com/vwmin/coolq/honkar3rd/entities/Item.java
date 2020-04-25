package com.vwmin.coolq.honkar3rd.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/22 21:09
 */
@Getter
@Setter
public class Item implements Cloneable{

    private String realName;
    private int rarity; //从1-5
    private int amount;

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
