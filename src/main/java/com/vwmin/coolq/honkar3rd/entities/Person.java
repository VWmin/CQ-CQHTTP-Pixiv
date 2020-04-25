package com.vwmin.coolq.honkar3rd.entities;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/22 16:53
 */
public class Person {
    private int total = 0;
    private int nothing = 0;

    public void increase(boolean isValuable){
        total++;
        if (isValuable){
            nothing = 0;
        }else {
            nothing++;
        }
    }

    public boolean needSomethingBetter(int baodi ){
        return nothing == baodi - 1;
    }

}
