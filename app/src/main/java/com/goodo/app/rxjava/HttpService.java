package com.goodo.app.rxjava;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     *
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

    /**
     * 添加日程
     */
    @GET("EduPlate/MSGSchedule/InterfaceJson.asmx/MySchedule_AddNew")
    Observable<ResponseBody> addSchedule(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                         @Query("UserName") String userName, @Query("Date") String date,
                                         @Query("IsAllDay") boolean isAllDay, @Query("BeginTime") String beginTime,
                                         @Query("EndTime") String endTime, @Query("Work") String work,
                                         @Query("Content") String content, @Query("Address") String address,
                                         @Query("RelatedUser") String user, @Query("Case_ID") int caseId,
                                         @Query("CaseName") String caseName);

    /**
     * 编辑日程
     */
    @GET("EduPlate/MSGSchedule/InterfaceJson.asmx/MySchedule_Save")
    Observable<ResponseBody> editSchedule(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                          @Query("MySchedule_ID") int id, @Query("Date") String date,
                                          @Query("IsAllDay") boolean isAllDay, @Query("BeginTime") String beginTime,
                                          @Query("EndTime") String endTime, @Query("Work") String work,
                                          @Query("Content") String content, @Query("Address") String address,
                                          @Query("RelatedUser") String user, @Query("Case_ID") int caseId,
                                          @Query("CaseName") String caseName);

    /**
     * 删除日程
     */
    @GET("EduPlate/MSGSchedule/InterfaceJson.asmx/MySchedule_Delete")
    Observable<ResponseBody> deleteSchedule(@Query("SessionID") String sessionID, @Query("User_ID") int userId,
                                            @Query("MySchedule_ID") int id);

    /**
     * 获取绑定的外部邮箱
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/OuterMailAddr_ListGet")
    Observable<ResponseBody> getOuterMail(@Query("SessionID") String sessionId, @Query("User_ID") int userId);

    /**
     * 获取内部电函分类
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/Receive_ClassifyGetList")
    Observable<ResponseBody> getInnerClassify(@Query("SessionID") String sessionId, @Query("User_ID") int userId);

    /**
     * 获取所有邮箱收件箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/TotalReceive_GetListByCache")
    Observable<ResponseBody> getAllEmailReceiveList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                    @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取所有邮箱发件箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/TotalMail_GetListByCache")
    Observable<ResponseBody> getAllEmailSendList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                 @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取所有邮箱草稿箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/EMail_DraftListGet")
    Observable<ResponseBody> getAllEmailDraftList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                  @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取所有邮箱回收箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/EMail_RecycleListGet")
    Observable<ResponseBody> getAllEmailTrashList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                  @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取内部电函收件箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/Receive_GetListByCache")
    Observable<ResponseBody> getInnerEmailReceiveList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                      @Query("ReceiveClassify_ID") int receiveClassifyId,
                                                      @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取内部电函发件箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/Mail_GetListByCache")
    Observable<ResponseBody> getInnerEmailSendList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                   @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取外部邮箱收件箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/OuterReceive_GetListByCache")
    Observable<ResponseBody> getOuterEmailReceiveList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                      @Query("OuterMailAddr_ID") int id,
                                                      @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取外部邮箱发件箱列表
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/OuterMail_GetListByCache")
    Observable<ResponseBody> getOuterEmailSendList(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                                   @Query("OuterMailAddr_ID") int id,
                                                   @Query("nPage") int page, @Query("nPageSize") int size);

    /**
     * 获取邮件详情
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/EMail_GetSingleInfo")
    Observable<ResponseBody> getEmailDetail(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                            @Query("EMail_ID") int emailId, @Query("IsInBox") int isInBox);

    /**
     * 获取单位信息
     */
    @GET("BasePlate/Interface/IInterfaceJson.asmx/OrgTree_Get")
    Observable<ResponseBody> getUnitInfo(@Query("User_ID") int userId, @Query("Unit_ID") int unitId,
                                         @Query("SessionID") String sessionId);

    /**
     * 获取单位人员
     */
    @GET("BasePlate/Interface/IInterfaceJson.asmx/User_GetList")
    Observable<ResponseBody> getUnitUser(@Query("User_ID") int userId, @Query("Unit_ID") int unitId,
                                         @Query("SessionID") String sessionId, @Query("Org_ID") int id,
                                         @Query("IsListChild") boolean isListChild);

    /**
     * 发送内部电函邮件
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/Mail_Send")
    @FormUrlEncoded
    Observable<ResponseBody> sendInnerEmail(@Field("SessionID") String sessionId, @Field("Mail_ID") int mailId,
                                            @Field("Subject") String subject, @Field("Body") String body,
                                            @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                            @Field("To") String toName, @Field("Cc") String ccName,
                                            @Field("Bcc") String bccName, @Field("ToIDs") String toIds,
                                            @Field("CcIDs") String ccIds, @Field("BccIDs") String bccIds,
                                            @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                            @Field("CaseName") String caseName, @Field("IsEncrypt") int isEncrypt,
                                            @Field("EncryptPWD") String pwd, @Field("IsSplitSend") int isSplitSend,
                                            @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                            @Field("Base64Datas") String base64Data);

    /**
     * 发送外部邮箱邮件
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/OuterMail_Send")
    @FormUrlEncoded
    Observable<ResponseBody> sendOuterEmail(@Field("SessionID") String sessionId, @Field("Mail_ID") int mailId,
                                            @Field("OuterMailAddr_ID") int outerMailId,
                                            @Field("Subject") String subject, @Field("Body") String body,
                                            @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                            @Field("To") String toName, @Field("Cc") String ccName,
                                            @Field("Bcc") String bccName,
                                            @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                            @Field("CaseName") String caseName, @Field("IsSplitSend") int isSplitSend,
                                            @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                            @Field("Base64Datas") String base64Data);

    /**
     * 存草稿
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/Mail_Save")
    @FormUrlEncoded
    Observable<ResponseBody> emailToTrash(@Field("SessionID") String sessionId, @Field("Mail_ID") int mailId,
                                          @Field("Subject") String subject, @Field("Body") String body,
                                          @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                          @Field("To") String toName, @Field("Cc") String ccName,
                                          @Field("Bcc") String bccName, @Field("ToIDs") String toIds,
                                          @Field("CcIDs") String ccIds, @Field("BccIDs") String bccIds,
                                          @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                          @Field("CaseName") String caseName, @Field("IsEncrypt") int isEncrypt,
                                          @Field("EncryptPWD") String pwd, @Field("IsSplitSend") int isSplitSend,
                                          @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                          @Field("Base64Datas") String base64Data);

    /**
     * 外部邮箱存草稿
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/OuterMail_Save")
    @FormUrlEncoded
    Observable<ResponseBody> outerEmailToTrash(@Field("SessionID") String sessionId, @Field("Mail_ID") int mailId,
                                               @Field("OuterMailAddr_ID") int outerMailId,
                                               @Field("Subject") String subject, @Field("Body") String body,
                                               @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                               @Field("To") String toName, @Field("Cc") String ccName,
                                               @Field("Bcc") String bccName,
                                               @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                               @Field("CaseName") String caseName, @Field("IsSplitSend") int isSplitSend,
                                               @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                               @Field("Base64Datas") String base64Data);

    /**
     * 收件人转发内部电函
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/Mail_RelayByReceiver")
    @FormUrlEncoded
    Observable<ResponseBody> receiverTransmitInnerEmail(@Field("SessionID") String sessionId, @Field("Receive_ID") int mailId,
                                                        @Field("Subject") String subject, @Field("Body") String body,
                                                        @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                                        @Field("To") String toName, @Field("Cc") String ccName,
                                                        @Field("Bcc") String bccName, @Field("ToIDs") String toIds,
                                                        @Field("CcIDs") String ccIds, @Field("BccIDs") String bccIds,
                                                        @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                                        @Field("CaseName") String caseName, @Field("IsEncrypt") int isEncrypt,
                                                        @Field("EncryptPWD") String pwd, @Field("IsSplitSend") int isSplitSend,
                                                        @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                                        @Field("Base64Datas") String base64Data);

    /**
     * 发件人转发内部电函
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/Mail_RelayBySender")
    @FormUrlEncoded
    Observable<ResponseBody> sendrTransmintInnerEmail(@Field("SessionID") String sessionId, @Field("Mail_ID") int mailId,
                                                      @Field("Subject") String subject, @Field("Body") String body,
                                                      @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                                      @Field("To") String toName, @Field("Cc") String ccName,
                                                      @Field("Bcc") String bccName, @Field("ToIDs") String toIds,
                                                      @Field("CcIDs") String ccIds, @Field("BccIDs") String bccIds,
                                                      @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                                      @Field("CaseName") String caseName, @Field("IsEncrypt") int isEncrypt,
                                                      @Field("EncryptPWD") String pwd, @Field("IsSplitSend") int isSplitSend,
                                                      @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                                      @Field("Base64Datas") String base64Data);

    /**
     * 收件人转发外部邮件
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/OuterMail_RelayByReceiver")
    @FormUrlEncoded
    Observable<ResponseBody> receiverTransmitOuterEmail(@Field("SessionID") String sessionId, @Field("Receive_ID") int mailId,
                                                        @Field("OuterMailAddr_ID") int outerMailId,
                                                        @Field("Subject") String subject, @Field("Body") String body,
                                                        @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                                        @Field("To") String toName, @Field("Cc") String ccName,
                                                        @Field("Bcc") String bccName,
                                                        @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                                        @Field("CaseName") String caseName, @Field("IsSplitSend") int isSplitSend,
                                                        @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                                        @Field("Base64Datas") String base64Data);

    /**
     * 发件人转发外部邮件
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/OuterMail_RelayBySender")
    @FormUrlEncoded
    Observable<ResponseBody> senderTransmitOuterEmail(@Field("SessionID") String sessionId, @Field("Mail_ID") int mailId,
                                                        @Field("OuterMailAddr_ID") int outerMailId,
                                                        @Field("Subject") String subject, @Field("Body") String body,
                                                        @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                                        @Field("To") String toName, @Field("Cc") String ccName,
                                                        @Field("Bcc") String bccName,
                                                        @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                                        @Field("CaseName") String caseName, @Field("IsSplitSend") int isSplitSend,
                                                        @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                                        @Field("Base64Datas") String base64Data);

    /**
     * 回复内部电函
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/Mail_Reply")
    @FormUrlEncoded
    Observable<ResponseBody> replyInnerEmail(@Field("SessionID") String sessionId, @Field("Receive_ID") int mailId,
                                             @Field("Subject") String subject, @Field("Body") String body,
                                             @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                             @Field("To") String toName, @Field("Cc") String ccName,
                                             @Field("Bcc") String bccName, @Field("ToIDs") String toIds,
                                             @Field("CcIDs") String ccIds, @Field("BccIDs") String bccIds,
                                             @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                             @Field("CaseName") String caseName, @Field("IsEncrypt") int isEncrypt,
                                             @Field("EncryptPWD") String pwd, @Field("IsSplitSend") int isSplitSend,
                                             @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                             @Field("Base64Datas") String base64Data);

    /**
     * 回复外部邮箱
     */
    @POST("EduPlate/MSGMail/InterfaceJson.asmx/OuterMail_Reply")
    @FormUrlEncoded
    Observable<ResponseBody> replyOuterEmail(@Field("SessionID") String sessionId, @Field("Receive_ID") int mailId,
                                             @Field("OuterMailAddr_ID") int outerMailId,
                                             @Field("Subject") String subject, @Field("Body") String body,
                                             @Field("SendUser_ID") int sendId, @Field("SendUserName") String sendName,
                                             @Field("To") String toName, @Field("Cc") String ccName,
                                             @Field("Bcc") String bccName,
                                             @Field("IsAttached") int isAttached, @Field("Case_ID") int caseId,
                                             @Field("CaseName") String caseName, @Field("IsSplitSend") int isSplitSend,
                                             @Field("OriginAttachs") String originAttaches, @Field("FileNames") String fileNames,
                                             @Field("Base64Datas") String base64Data);

    /**
     * 删除邮件
     */
    @GET("EduPlate/MSGMail/InterfaceJson.asmx/EMail_Delete")
    Observable<ResponseBody> deleteEmail(@Query("SessionID") String sessionId, @Query("User_ID") int userId,
                                         @Query("EMail_ID") int emailId, @Query("IsInBox") int isInBox,
                                         @Query("IsDel") int isDel);

    /**
     * 接收公文列表接口
     */
    @GET("EduPlate/jfyPublicDocument/IPublicDocument.asmx/ReceiveDocument_GetXmlByUser")
    Observable<ResponseBody> getReceiveDocument(@Query("User_ID") int userId);

    /**
     * 发送公文列表接口
     */
    @GET("EduPlate/jfyPublicDocument/IPublicDocument.asmx/SendDocument_GetXmlByUser")
    Observable<ResponseBody> getSendDocument(@Query("User_ID") int userId);

    /**
     * 接收公文详情
     */
    @GET("EduPlate/jfyPublicDocument/IPublicDocument.asmx/ReceiveDocument_GetSingle")
    Observable<ResponseBody> getReceiveDocDetail(@Query("Receive_ID") int id);

    /**
     * 发送公文详情
     */
    @GET("EduPlate/jfyPublicDocument/IPublicDocument.asmx/SendDocument_GetSingle")
    Observable<ResponseBody> getSendDocDetail(@Query("Send_ID") int sendId, @Query("UserID") int userId);

    /**
     * 通知列表信息
     */
    @GET("EduPlate/NoticePlate/JsonNotice.asmx/ReceiveNotice_Get")
    Observable<ResponseBody> getNoticeList(@Query("User_ID") int userId, @Query("Keyword") String keyword,
                                           @Query("SearchType") int searchType);

    /**
     * 获取通知详情
     */
    @GET("EduPlate/NoticePlate/JsonNotice.asmx/Notice_Get")
    Observable<ResponseBody> getNoticeDetail(@Query("Notice_ID") int noticeId);
}
