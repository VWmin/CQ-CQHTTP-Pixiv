package vwmin.coolq.function.pixiv.service;

import vwmin.coolq.function.pixiv.entity.IllustResponse;
import vwmin.coolq.function.pixiv.entity.ListIllustResponse;
import vwmin.coolq.function.pixiv.entity.UserResponse;
import vwmin.coolq.service.BaseService;

import java.io.IOException;

public interface PixivService extends BaseService {

    ListIllustResponse getRank(String mode, String date) throws IOException;

    IllustResponse getIllustById(Integer illustId) throws IOException;

    UserResponse getUserById(Integer userId) throws IOException;

    ListIllustResponse getIllustByWord(String word, String sort, String searchTarget) throws IOException;

    ListIllustResponse getNext(String nextUrl) throws IOException;

}
