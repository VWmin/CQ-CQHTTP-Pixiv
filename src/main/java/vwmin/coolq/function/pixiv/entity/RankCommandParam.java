package vwmin.coolq.function.pixiv.entity;

import vwmin.coolq.entity.CommandParam;

public final class RankCommandParam extends CommandParam {
    public RankCommandParam(String name, String value) {
        super(name, value);
    }

    public enum RankMode{
        /***/
        DAY("day"),
        WEEK("week"),
        MONTH("month"),
        DAY_MALE("day_male"),
        DAY_FEMALE("day_female"),
        WEEK_ORIGINAL("week_original"),
        WEEK_ROOKIE("week_rookie"),
        DAY_MANGA("day_manga"),
        DAY_R18("day_r18"),
        DAY_MALE_R18("day_male_r18"),
        DAY_FEMALE_R18("day_female_r18"),
        WEEK_R18("week_r18");


        private final String value;
        RankMode(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }

        public static RankMode ifExist(String name){
            try{
                return RankMode.valueOf(name.toUpperCase());
            }catch (IllegalArgumentException e){
                return RankMode.DAY;
            }
        }
    }

    public static RankCommandParam create(final String name, final String value) {
        return new RankCommandParam(name, value);
    }

    public static RankCommandParam rankMode(RankMode rankMode){
        return create("mode", rankMode.getValue());
    }
}
