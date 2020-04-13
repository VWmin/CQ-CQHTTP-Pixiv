package com.vwmin.coolq.exception;

import java.io.IOException;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/12 17:41
 */
public class EmptyDataException extends IOException {
    public EmptyDataException(String msg){
        super(msg);
    }
}
