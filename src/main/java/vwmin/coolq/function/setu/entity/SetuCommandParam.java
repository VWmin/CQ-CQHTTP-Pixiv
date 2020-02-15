package vwmin.coolq.function.setu.entity;

import vwmin.coolq.entity.CommandParam;

import java.util.ArrayList;
import java.util.List;

public final class SetuCommandParam extends CommandParam {

    public SetuCommandParam(String name, String value){
        super(name, value);
    }


    public enum R18{
        /**没有*/
        NO("0"),
        /**全是*/
        YES("1"),
        /**混合*/
        MAYBE("2");

        private final String value;
        R18(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }


        public static R18 ifExist(String s){
            switch (s){
                case "1":
                    return YES;
                case "2":
                    return MAYBE;
                default:
                    return NO;
            }
        }

    }

    public static SetuCommandParam create(final String name, final String value) {
        return new SetuCommandParam(name, value);
    }

    public static SetuCommandParam needR18(R18 isNeed){
        return create("r18", isNeed.getValue());
    }

    public static SetuCommandParam keyword(String keyword){
        return create("keyword", keyword);
    }

    public static SetuCommandParam num(Integer num){
        if (num < 1){
            num = 1;
        } else if (num > 10){
            num = 10;
        }
        return create("num", String.valueOf(num));
    }

    public static SetuCommandParam proxy(String proxy){
        //输入未检查
        return create("proxy", proxy);
    }

    public static SetuCommandParam thumbnail(boolean useThumbnail){
        return create("size1200", String.valueOf(useThumbnail));
    }

    public static List<SetuCommandParam> noParam(){
        return new ArrayList<>();
    }

}
