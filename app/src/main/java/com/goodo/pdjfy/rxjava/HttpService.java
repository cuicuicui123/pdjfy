package com.goodo.pdjfy.rxjava;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 网络请求接口
 */

public interface HttpService {
    /**
     * 获取首页轮播图片
     */
    @GET("web/wsjson.asmx/Get_Json_SubjectContentPicTopList")
    Observable<ResponseBody> getHomePageTopList(@Query("TopList") int topList);

    /**
     * 获取首页新闻列表
     */
    @GET("web/wsjson.asmx/{news}")
    Observable<ResponseBody> getHomePageNewsList(@Path("news") String news,
                                                 @Query("Page") int page, @Query("PageSize") int pageSize,
                                                 @Query("Keyword") String keyWord);
}
