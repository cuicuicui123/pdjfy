package com.goodo.pdjfy.rxjava;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
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

    /**
     * 获取首页轮播图片详情
     */
    @GET("web/wsjson.asmx/Get_Json_Content")
    Observable<ResponseBody> getHomePagePicDetail(@Query("Content_ID") String contentId);

    /**
     * 登录
     */
    @GET("EduPlate/MyScheduleForJfy/Interface.asmx/LoginUser")
    Observable<ResponseBody> login(@Query("LoginID") String account, @Query("EncyptPassword") String pwd,
                                   @Query("OnlineType") String onLineType, @Query("DeviceName") String deviceName,
                                   @Query("Place") String place);

    /**
     * 下载文件
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> downLoad(@Url String url);

    /**
     * 获得院级公告列表
     */
    @GET("web/wsjson.asmx/Get_Json_SubjectContentSearchList")
    Observable<ResponseBody> getAnnounceList(@Query("Page") int page, @Query("PageSize") int pageSize,
                                             @Query("Keyword") String keyword);

    /**
     * 获得院级公告详情
     */
    @GET("web/wsjson.asmx/Get_Json_Content")
    Observable<ResponseBody> getAnnounceDetail(@Query("Content_ID") String contentId);

    /**
     * 获得院级公告分类列表
     */
    @GET("web/wsjson.asmx/Get_Json_SubjectChlidList")
    Observable<ResponseBody> getAnnounceClassifyList();

    /**
     * 获得院级公告某一分类的列表
     */
    @GET("web/wsjson.asmx/Get_Json_SubjectContentSearchListByID")
    Observable<ResponseBody> getAnnounceClassifyOneList(@Query("Subjects_ID") String id, @Query("Page") int page,
                                                        @Query("PageSize") int pageSize, @Query("Keyword") String keyword);

    /**
     * 获得日程
     */
    @GET("EduPlate/MSGSchedule/InterfaceJson.asmx/Schedule_GetTotalList")
    Observable<ResponseBody> getSchedule(@Query("SessionID") String sessionId, @Query("User_ID") int userID,
                                         @Query("Unit_ID") int unitId, @Query("BeginDay") String beginDay,
                                         @Query("EndDay") String endDay);

    /**
     * 获得日程详情
     */
    @GET("EduPlate/MSGSchedule/InterfaceJson.asmx/Schedule_GetSingle")
    Observable<ResponseBody> getScheduleDetail(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                               @Query("Unit_ID") int unitId, @Query("ID") int id,
                                               @Query("Type") int type);

}
