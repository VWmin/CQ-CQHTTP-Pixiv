package com.vwmin.coolq.honkar3rd;


import com.vwmin.coolq.honkar3rd.entities.Character;
import com.vwmin.coolq.honkar3rd.entities.Equipment;
import com.vwmin.coolq.honkar3rd.entities.Item;
import com.vwmin.coolq.honkar3rd.entities.Stigma;

import java.util.Comparator;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/23 23:06
 */
public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        /* 各项物品排列存在优先级，以角色扩充为例
           1. S角色 A角色 B角色
           2. S角色碎片 类推
           3. 火锅底料装备圣痕 装备在前 圣痕在后 按星级排序
           4. 各种素材 按星级排序
           首先进行不同类型之间排序，然后进行同类型之间排序
         */
        int priority1 = getPriority(o1);
        int priority2 = getPriority(o2);

        return priority1 - priority2;
    }

    private int getPriority(Item o){
        if (o instanceof Character){
            // 角色卡在碎片前 分布区间0 - 20
            return !((Character) o).isDebris() ? (10 - o.getRarity()) : 10 + (10 - o.getRarity());
        }else if (o instanceof Equipment){
            // 同星级装备在圣痕前，不同星级高星在前  20 - 40
            int p = 20 + (5 - o.getRarity()) * 5;
            return p + ((o instanceof Stigma) ? 1 : 0);
        }

        // 材料
        return 40 + (10 - o.getRarity());
    }
}
