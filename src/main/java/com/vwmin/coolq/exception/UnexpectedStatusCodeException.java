package com.vwmin.coolq.exception;

import java.io.IOException;

/**
 * @author vwmin
 * @version 1.0
 * @date 2020/4/16 22:16
 */
public class UnexpectedStatusCodeException extends IOException {
    private int code;
    public UnexpectedStatusCodeException(int code, String msg){
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
