package com.vwmin.coolq.honkar3rd.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/22 21:54
 */
@Getter
@Setter
public class Stigma extends Equipment implements Cloneable{
    private int position;

    @Override
    public Item clone() {
        Stigma clone = (Stigma) super.clone();
        clone.setPosition(this.getPosition());
        return clone;
    }

    public static String getStigmaPosition(int pos){
        switch (pos){
            case 0:
                return "上";
            case 1:
                return "中";
            default:
                return "下";
        }
    }
}
