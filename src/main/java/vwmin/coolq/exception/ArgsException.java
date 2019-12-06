package vwmin.coolq.exception;

import org.apache.commons.cli.ParseException;

public class ArgsException extends RuntimeException {
    ArgsException(String message){
        super(message);
    }
}
