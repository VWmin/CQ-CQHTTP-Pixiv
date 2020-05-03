package com.vwmin.coolq.pixiv.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/13 13:09
 */
@Data
public class Illusts implements Serializable {

    public Illusts(){}

    public Illusts( List<Illust> illusts){
        this.illusts = illusts;
    }

    private List<Illust> illusts;
    private String next_url ;


}
