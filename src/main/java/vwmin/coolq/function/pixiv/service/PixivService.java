package vwmin.coolq.function.pixiv.service;

import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.service.BaseService;

import java.io.IOException;

public interface PixivService extends BaseService {

    public ListIllustResponse getRank(String mode, String date) throws IOException;

    public IllustResponse getIllustById(Integer illustId) throws IOException;

    public UserResponse getUserById(Integer userId) throws IOException;

    public ListIllustResponse getIllustByWord(String word, String sort, String searchTarget) throws IOException;

    public ListIllustResponse getNext(String nextUrl) throws IOException;

}
