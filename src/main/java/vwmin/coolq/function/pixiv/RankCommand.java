package vwmin.coolq.function.pixiv;

import vwmin.coolq.function.Command;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.RankCommandParam;
import vwmin.coolq.function.pixiv.service.PixivService;

import java.io.IOException;
import java.util.List;

public class RankCommand implements Command<ListIllustResponse> {

    private PixivService pixivService;
    private List<RankCommandParam> params;

    public RankCommand(PixivService pixivService, List<RankCommandParam> params){
        this.pixivService = pixivService;
        this.params = params;
    }

    public void setParams(List<RankCommandParam> params) {
        this.params = params;
    }

    @Override
    public ListIllustResponse execute() throws IOException {
        String mode = null;
        String date = null;
        for (RankCommandParam param:params){
            switch (param.name()){
                case "mode":
                    mode = param.value();
                    break;
                case "date":
                    date = param.value();
                    break;
                default:
                    break;
            }
        }
        return pixivService.getRank(mode, date);
    }
}
