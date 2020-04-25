package com.vwmin.coolq.honkar3rd.gacha;

import java.util.Random;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/24 13:05
 */
public class RandomUtil {
    private RandomUtil(){}
    private final static Random RAND = new Random();
    public static int nextInt(int lowBound, int topBound){
        if (topBound <= lowBound){
            throw new IllegalArgumentException("topBound must bigger than lowBound.");
        }
        return RAND.nextInt(topBound - lowBound) + lowBound;
    }
}
