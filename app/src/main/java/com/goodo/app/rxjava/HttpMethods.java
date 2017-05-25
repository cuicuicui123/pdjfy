package com.goodo.app.rxjava;

import android.net.Uri;
import android.util.Log;

import com.goodo.app.email.model.SendInnerEmailBean;
import com.goodo.app.email.model.SendOuterEmailBean;
import com.goodo.app.homepage.model.LoginBean;
import com.goodo.app.schedule.model.AddScheduleBean;
import com.goodo.app.util.MyConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description RxJava + Retrofit网络请求单例类
 */

public class HttpMethods {
    private Retrofit mRetrofit;
    private Interceptor mInterceptor;
    private HttpService mHttpService;

    public static String BASE_URL = "http://jfy.pudong-edu.sh.cn/";
    private static final int DEFAULT_TIMEOUT = 60;//默认超时时间60秒
    private static volatile HttpMethods mInstance;

    private String mUrlType = "application/x-www-form-urlencoded";
    private String mMultiPartType = "multipart/form-data";
    private String mTextType = "";

    /**
     * 初始化HttpMethods
     * 手动创建一个OkHttpClient并设置超时时间
     */
    private HttpMethods(){
        /**
         * 拦截url，用于调试时打印url
         */
        mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.i("url", chain.request().url().toString());
//                if (chain.request().method().equals("POST")) {
//                    Request original = chain.request();
//
//                    Request request = original.newBuilder()
//                            .method(original.method(), new ProgressRequestBody(original.body(), new ProgressRequestListener() {
//                                @Override
//                                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
//                                    Log.i("progress", bytesWritten / contentLength + "");
//                                }
//                            }))
//                            .build();
//                    return chain.proceed(request);
//                }
                return chain.proceed(chain.request());
            }
        };
        /**
         * 处理错误
         */
        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                Log.w("Error", e);
            }
        });
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addNetworkInterceptor(mInterceptor);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mHttpService = mRetrofit.create(HttpService.class);
    }

    /**
     *  单例，双重检验加锁
     */
    public static HttpMethods getInstance(){
        if (mInstance == null) {
            synchronized (HttpMethods.class) {
                if (mInstance == null) {
                    mInstance = new HttpMethods();
                }
            }
        }
        return mInstance;
    }

    private void doSubscribe(Observable observable, Subscriber subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获得首页顶部轮播图
     * @param topList 图片数量
     * @param subscriber
     */
    public void getHomePageTopList(int topList, Subscriber subscriber){
        Observable observable = mHttpService.getHomePageTopList(topList);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获得新闻列表
     * @param news 方法名
     * @param page 页数
     * @param pageSize 一页数量
     * @param keyword 关键字
     * @param subscriber
     */
    public void getHomePageNewsList(String news, int page, int pageSize, String keyword, Subscriber subscriber){
        Observable observable = mHttpService.getHomePageNewsList(
                news, page, pageSize, keyword);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取首页图片信息详情
     * @param contentId
     */
    public void getHomePagePicDetail(String contentId, Subscriber subscriber){
        Observable observable = mHttpService.getHomePagePicDetail(contentId);
        doSubscribe(observable, subscriber);
    }

    /**
     * 登录
     * @param loginBean
     * @param subscriber
     */
    public void login(LoginBean loginBean, Subscriber subscriber){
        Observable observable = mHttpService.login(loginBean.getAccount(), loginBean.getPwd(),
                loginBean.getOnLineType(), loginBean.getDeviceName(), loginBean.getPlace());
        doSubscribe(observable, subscriber);
    }

    /**
     * 下载文件Base64编码
     * @param url
     * @param subscriber
     */
    public void downLoad(String url, Subscriber subscriber){
        Observable observable = mHttpService.downLoad(url);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获得公告列表
     */
    public void getAnnounceList(int page, int pageSize, String keyword, Subscriber subscriber){
        Observable observable = mHttpService.getAnnounceList(page, pageSize, keyword);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获得公告详情
     */
    public void getAnnounceDetail(String contentId, Subscriber subscriber){
        Observable observable = mHttpService.getAnnounceDetail(contentId);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获得公告分类列表
     */
    public void getAnnounceClassifyList(Subscriber subscriber){
        Observable observable = mHttpService.getAnnounceClassifyList();
        doSubscribe(observable, subscriber);
    }

    /**
     * 获得某一分类公告列表
     */
    public void getAnnounceClassifyOneList(String id, int page, int pageSize, String keyword
            , Subscriber subscriber){
        Observable observable = mHttpService.getAnnounceClassifyOneList(id, page, pageSize, keyword);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获得日程
     */
    public void getSchedule(String sessionId, int userId, int unitId, String beginDay, String endDay,
                            Subscriber subscriber){
        Observable observable = mHttpService.getSchedule(sessionId, userId, unitId, beginDay, endDay);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获得日程详情
     */
    public void getScheduleDetail(String sessionId, int userId, int unitId, int id, int type, Subscriber subscriber){
        Observable observable = mHttpService.getScheduleDetail(sessionId, userId, unitId, id, type);
        doSubscribe(observable, subscriber);
    }

    /**
     * 添加日程
     */
    public void addSchedule(AddScheduleBean bean, Subscriber subscriber) {
        Observable observable = mHttpService.addSchedule(MyConfig.SESSION_ID, MyConfig.USER_ID, MyConfig.USERNAME,
                bean.getDate(), bean.getIsAllDay() == MyConfig.IS_ALL_DAY, bean.getBeginTime(), bean.getEndTime(), bean.getWork(),
                bean.getContent(), bean.getAddress(), bean.getRelatedUser(), bean.getCaseId(), bean.getCaseName());
        doSubscribe(observable, subscriber);
    }

    /**
     * 编辑日程
     */
    public void editSchedule(AddScheduleBean bean, Subscriber subscriber){
        Observable observable = mHttpService.editSchedule(MyConfig.SESSION_ID, MyConfig.USER_ID, bean.getId(),
                bean.getDate(), bean.getIsAllDay() == MyConfig.IS_ALL_DAY, bean.getBeginTime(), bean.getEndTime(), bean.getWork(),
                bean.getContent(), bean.getAddress(), bean.getRelatedUser(), bean.getCaseId(), bean.getCaseName());
        doSubscribe(observable, subscriber);
    }

    /**
     * 删除日程
     */
    public void deleteSchedule(int scheduleId, Subscriber subscriber){
        Observable observable = mHttpService.deleteSchedule(MyConfig.SESSION_ID, MyConfig.USER_ID, scheduleId);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取外部邮箱
     */
    public void getOuterMail(Subscriber subscriber){
        Observable observable = mHttpService.getOuterMail(MyConfig.SESSION_ID, MyConfig.USER_ID);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取内部电函分类
     */
    public void getInnerClassify(Subscriber subscriber){
        Observable observable = mHttpService.getInnerClassify(MyConfig.SESSION_ID, MyConfig.USER_ID);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取所有邮箱收件箱列表
     */
    public void getAllEmailReceiveList(int page, int size, Subscriber subscriber){
        Observable observable = mHttpService.getAllEmailReceiveList(MyConfig.SESSION_ID, MyConfig.USER_ID, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取所有邮箱发件箱列表
     */
    public void getAllEmailSendList(int page, int size, Subscriber subscriber){
        Observable observable = mHttpService.getAllEmailSendList(MyConfig.SESSION_ID, MyConfig.USER_ID, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取所有邮箱草稿箱列表
     */
    public void getAllEmailDraftList(int page, int size, Subscriber subscriber){
        Observable observable = mHttpService.getAllEmailDraftList(MyConfig.SESSION_ID, MyConfig.USER_ID, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取所有邮箱回收箱列表
     */
    public void getAllEmailTrashList(int page, int size, Subscriber subscriber){
        Observable observable = mHttpService.getAllEmailTrashList(MyConfig.SESSION_ID, MyConfig.USER_ID, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取内部电函收件箱列表
     */
    public void getInnerEmailReceiveList(int page, int size, int id, Subscriber subscriber){
        Observable observable = mHttpService.getInnerEmailReceiveList(MyConfig.SESSION_ID, MyConfig.USER_ID, id, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取内部电函发件箱列表
     */
    public void getInnerEmailSendList(int page, int size, Subscriber subscriber){
        Observable observable = mHttpService.getInnerEmailSendList(MyConfig.SESSION_ID, MyConfig.USER_ID, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取外部邮箱收件箱列表
     */
    public void getOuterEmailReceiveList(int page, int size, int id, Subscriber subscriber){
        Observable observable = mHttpService.getOuterEmailReceiveList(MyConfig.SESSION_ID, MyConfig.USER_ID, id, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取外部邮箱发件箱列表
     */
    public void getOuterEmailSendList(int page, int size, int id, Subscriber subscriber){
        Observable observable = mHttpService.getOuterEmailSendList(MyConfig.SESSION_ID, MyConfig.USER_ID, id, page, size);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取邮件详情
     */
    public void getEmailDetail(int id, int isInBox, Subscriber subscriber){
        Observable observable = mHttpService.getEmailDetail(MyConfig.SESSION_ID, MyConfig.USER_ID, id, isInBox);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取单位信息
     */
    public void getUnitInfo(Subscriber subscriber){
        Observable observable = mHttpService.getUnitInfo(MyConfig.USER_ID, MyConfig.UNIT_ID, MyConfig.SESSION_ID);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取单位人员
     */
    public void getUnitGroupUser(int id, Subscriber subscriber){
        Observable observable = mHttpService.getUnitUser(MyConfig.USER_ID, MyConfig.UNIT_ID, MyConfig.SESSION_ID, id, true);
        doSubscribe(observable, subscriber);
    }

    /**
     * 发送内部电函
     * -1代表没有id，是发送一封信的邮件
     */
    public void sendInnerEmail(SendInnerEmailBean bean, Subscriber subscriber){
        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter("Subject", bean.getSubject());
        builder.appendQueryParameter("Body", bean.getBody());
        builder.appendQueryParameter("SendUser_ID", MyConfig.USER_ID + "");
        builder.appendQueryParameter("SendUserName", MyConfig.USERNAME);
        builder.appendQueryParameter("To", bean.getToName());
        builder.appendQueryParameter("Cc", bean.getCcName());
        builder.appendQueryParameter("Bcc", bean.getBccName());
        builder.appendQueryParameter("ToIDs", bean.getToIds());
        builder.appendQueryParameter("CcIDs", bean.getCcIds());
        builder.appendQueryParameter("BccIDs", bean.getBccIds());
        builder.appendQueryParameter("IsAttached", "1");
        builder.appendQueryParameter("Case_ID", "0");
        builder.appendQueryParameter("CaseName", "无");
        builder.appendQueryParameter("IsEncrypt", "0");
        builder.appendQueryParameter("EncryptPWD", "");
        builder.appendQueryParameter("IsSplitSend", "0");
        builder.appendQueryParameter("OriginAttachs", bean.getOriginAttachs());
        builder.appendQueryParameter("FileNames", bean.getFileNames());
        builder.appendQueryParameter("Base64Datas", bean.getBase64Data());
        String url = builder.toString();

        //构造上传请求，类似web表单
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), url);
        ProgressRequestBody progressRequestBody = new ProgressRequestBody(requestBody, new ProgressRequestListener() {
            @Override
            public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                Log.i("progress", bytesWritten / contentLength + "");
            }
        });
        //这里是发送请求代码
        Observable observable = mHttpService.sendInnerEmail2(progressRequestBody);
        doSubscribe(observable, subscriber);

//        Observable observable = mHttpService.sendInnerEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getSubject(),
//                bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(),
//                bean.getBccName(), bean.getToIds(), bean.getCcIds(), bean.getBccIds(), 1, 0, "无", 0, "",
//                0, bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
//        doSubscribe(observable, subscriber);
    }

    /**
     * 发送外部邮件
     */
    public void sendOuterEmail(SendOuterEmailBean bean, Subscriber subscriber){
        Observable observable = mHttpService.sendOuterEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getOuterMailAddrId(),
                bean.getSubject(), bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(),
                bean.getCcName(), bean.getBccName(), 1, 0, "无", 0, bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
        doSubscribe(observable, subscriber);
    }

    /**
     * 内部电函存草稿
     */
    public void innerEmailToTrash(SendInnerEmailBean bean, Subscriber subscriber){
        Observable observable = mHttpService.emailToTrash(MyConfig.SESSION_ID, bean.getEmailId(), bean.getSubject(),
                bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(),
                bean.getBccName(), bean.getToIds(), bean.getCcIds(), bean.getBccIds(), 1, 0, "无", 0, "",
                0, "", bean.getFileNames(), bean.getBase64Data());
        doSubscribe(observable, subscriber);
    }

    /**
     * 外部邮件存草稿
     */
    public void outerEmailToTrash(SendOuterEmailBean bean, Subscriber subscriber){
        Observable observable = mHttpService.outerEmailToTrash(MyConfig.SESSION_ID, bean.getEmailId(), bean.getOuterMailAddrId(),
                bean.getSubject(), bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(),
                bean.getCcName(), bean.getBccName(), 1, 0, "无", 0, "", bean.getFileNames(), bean.getBase64Data());
        doSubscribe(observable, subscriber);
    }

    /**
     * 转发内部电函
     */
    public void transmitInnerEmail(SendInnerEmailBean bean, int isInBox, Subscriber subscriber){
        Observable observable;
        if (isInBox == MyConfig.IS_INBOX) {
            observable = mHttpService.receiverTransmitInnerEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getSubject(),
                    bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(), bean.getBccName(), bean.getToIds(),
                    bean.getCcIds(), bean.getBccIds(), 1, 0, "无", 0, "", 0, bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
        } else {
            observable = mHttpService.sendTransmitInnerEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getSubject(),
                    bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(), bean.getBccName(), bean.getToIds(),
                    bean.getCcIds(), bean.getBccIds(), 1, 0, "无", 0, "", 0, bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
        }
        doSubscribe(observable, subscriber);
    }

    /**
     * 转发外部邮件
     */
    public void transmitOuterEmail(SendOuterEmailBean bean, int isInbox, Subscriber subscriber){
        Observable observable;
        if (isInbox == MyConfig.IS_INBOX) {
            observable = mHttpService.receiverTransmitOuterEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getOuterMailAddrId(), bean.getSubject(),
                    bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(), bean.getBccName(), 1, 0, "无", 0,
                    bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
        } else {
            observable = mHttpService.senderTransmitOuterEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getOuterMailAddrId(), bean.getSubject(),
                    bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(), bean.getBccName(), 1, 0, "无", 0,
                    bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
        }
        doSubscribe(observable, subscriber);
    }

    /**
     * 回复内部邮件
     */
    public void replyInnerEmail(SendInnerEmailBean bean, Subscriber subscriber){
        Observable observable = mHttpService.replyInnerEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getSubject(),
                bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(), bean.getBccName(), bean.getToIds(),
                bean.getCcIds(), bean.getBccIds(), 1, 0, "无", 0, "", 0, bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
        doSubscribe(observable, subscriber);
    }

    /**
     * 回复外部邮件
     */
    public void replyOuterEmail(SendOuterEmailBean bean, Subscriber subscriber){
        Observable observable = mHttpService.replyOuterEmail(MyConfig.SESSION_ID, bean.getEmailId(), bean.getOuterMailAddrId(), bean.getSubject(),
                bean.getBody(), MyConfig.USER_ID, MyConfig.USERNAME, bean.getToName(), bean.getCcName(), bean.getBccName(), 1, 0, "无", 0,
                bean.getOriginAttachs(), bean.getFileNames(), bean.getBase64Data());
        doSubscribe(observable, subscriber);
    }

    /**
     * 删除邮件
     */
    public void deleteEmail(int id, int isInBox, int isDel, Subscriber subscriber){
        Observable observable = mHttpService.deleteEmail(MyConfig.SESSION_ID, MyConfig.USER_ID, id, isInBox, isDel);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取接收公文
     */
    public void getReceiveDocumentList(Subscriber subscriber){
        Observable observable = mHttpService.getReceiveDocument(MyConfig.USER_ID);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取发送公文
     */
    public void getSendDocumentList(Subscriber subscriber){
        Observable observable = mHttpService.getSendDocument(MyConfig.USER_ID);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取接收公文详情
     */
    public void getReceiveDocDetail(int id, Subscriber subscriber){
        Observable observable = mHttpService.getReceiveDocDetail(id);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取发送公文详情
     */
    public void getSendDocDetail(int id, Subscriber subscriber){
        Observable observable = mHttpService.getSendDocDetail(id, MyConfig.USER_ID);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取通知列表
     */
    public void getNoticeList(Subscriber subscriber){
        Observable observable = mHttpService.getNoticeList(MyConfig.USER_ID, "", -1);
        doSubscribe(observable, subscriber);
    }

    /**
     * 获取通知详情
     */
    public void getNoticeDetail(int noticeId, Subscriber subscriber){
        Observable observable = mHttpService.getNoticeDetail(noticeId);
        doSubscribe(observable, subscriber);
    }

    public HttpService getHttpService(){
        return mHttpService;
    }
}
