package com.goodo.pdjfy.rxjava;

import android.util.Log;

import com.goodo.pdjfy.homepage.model.LoginBean;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
        builder.addInterceptor(mInterceptor);
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

    private void doSubscribe(Observable observable, Subscriber subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
