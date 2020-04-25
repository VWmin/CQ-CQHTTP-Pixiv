package com.vwmin.coolq.honkar3rd.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/22 21:52
 */
@Getter
@Setter
public class Material extends Item implements Cloneable {
    @Override
    public Item clone() {
        return super.clone();
    }
}
