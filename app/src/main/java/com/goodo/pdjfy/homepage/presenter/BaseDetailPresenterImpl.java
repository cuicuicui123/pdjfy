package com.goodo.pdjfy.homepage.presenter;

import android.content.Intent;

import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.LoginActivity;
import com.goodo.pdjfy.homepage.view.HomePageNewsDetailView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public abstract class BaseDetailPresenterImpl implements HomePageDetailPresenter {
    protected HomePageNewsDetailView mDetailView;
    private AppContext mAppContext;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;

    private static String KEY_HOMEPAGE_PIC_DETAIL = "getHomePagePicDetail";

    public BaseDetailPresenterImpl(HomePageNewsDetailView detailView, BaseActivity activity) {
        mDetailView = detailView;
        mAppContext = AppContext.getInstance();
        mHttpMethods = HttpMethods.getInstance();
        mActivity = activity;
    }

    @Override
    public void getHomePagePicDetail(String contentId) {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_HOMEPAGE_PIC_DETAIL + contentId) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getHomePagePicDetail(contentId, cacheSubscriber);
    }

    @Override
    public void startToLoginActivity() {
        Intent it = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(it);
    }

    protected abstract void handleResponse(String response);
}
