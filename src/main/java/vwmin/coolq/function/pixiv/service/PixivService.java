package vwmin.coolq.function.pixiv.service;

import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.service.BaseService;

public interface PixivService extends BaseService {

    public ListIllustResponse getRank(String mode, String date);

    public IllustResponse getIllustById(Integer illust_id);

    public UserResponse getUserById(Integer user_id);

    public ListIllustResponse getIllustByWord(String word, String sort, String search_target);

    public ListIllustResponse getNext(String next_url);

}
