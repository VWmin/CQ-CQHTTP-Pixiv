package com.vwmin.coolq.function.pixiv.entity;




//public final class WordCommandParam extends CommandParam {
//    public WordCommandParam(String name, String value) {
//        super(name, value);
//    }
//
//    public enum SortByDate{
//        /**最新*/
//        DESC("date_desc"),
//        /**从旧到新*/
//        ASC("date_asc");
//
//        private final String value;
//        SortByDate(String value) {
//            this.value = value;
//        }
//        public String getValue() {
//            return value;
//        }
//
//        public static SortByDate ifExist(String name){
//            try{
//                return SortByDate.valueOf(name);
//            }catch (IllegalArgumentException e){
//                return SortByDate.DESC;
//            }
//        }
//
//    }
//
//    public enum SearchTarget{
//        /**标签完全匹配*/
//        EXACT("exact_match_for_tags"),
//        /**标签部分匹配*/
//        PARTIAL("partial_match_for_tags"),
//        /**标题或简介*/
//        BROAD("title_and_caption");
//
//        private final String value;
//        SearchTarget(String value) {
//            this.value = value;
//        }
//        public String getValue() {
//            return value;
//        }
//        public static SearchTarget ifExist(String name){
//            try{
//                return SearchTarget.valueOf(name.toUpperCase());
//            }catch (IllegalArgumentException e){
//                return SearchTarget.BROAD;
//            }
//        }
//    }
//
//    public static WordCommandParam word(String word){
//        return create("word", word);
//    }
//
//    public static WordCommandParam sort(SortByDate sortByDate){
//        return create("sort", sortByDate.getValue());
//    }
//
//    public static WordCommandParam target(SearchTarget searchTarget){
//        return create("search_target", searchTarget.getValue());
//    }
//
//    public static WordCommandParam create(final String name, final String value) {
//        return new WordCommandParam(name, value);
//    }
//
//}
