package com.vwmin.coolq.function.pixiv;

//import vwmin.coolq.function.Command;
//import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
//import vwmin.coolq.function.pixiv.entity.WordCommandParam;
//import vwmin.coolq.function.pixiv.service.PixivService;
//
//import java.io.IOException;
//import java.util.List;
//
//public class WordCommand implements Command<ListIllustResponse> {
//    private PixivService service;
//    private List<WordCommandParam> params;
//
//    public WordCommand(PixivService service, List<WordCommandParam> params){
//        this.service = service;
//        this.params = params;
//    }
//
//    public void setParams(List<WordCommandParam> params) {
//        this.params = params;
//    }
//
//    @Override
//    public ListIllustResponse execute() throws IOException {
//        String word = null;
//        String sort = null;
//        String target = null;
//
//        for (WordCommandParam param:params){
//            switch (param.name()){
//                case "word":
//                    word = param.value();
//                    break;
//                case "sort":
//                    sort = param.value();
//                    break;
//                case "search_target":
//                    target = param.value();
//                default:
//                    break;
//            }
//        }
//
//        return service.getIllustByWord(word, sort, target);
//    }
//}
