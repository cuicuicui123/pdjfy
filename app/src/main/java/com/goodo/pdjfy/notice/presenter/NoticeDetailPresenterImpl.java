package com.goodo.pdjfy.notice.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.notice.model.NoticeDetailBean;
import com.goodo.pdjfy.notice.view.NoticeDetailView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class NoticeDetailPresenterImpl implements NoticeDetailPresenter {
    private NoticeDetailView mDetailView;
    private BaseActivity mActivity;
    private HttpMethods mHttpMethods;
    private int mId;
    private String KEY = "getNoticeDetail";


    public NoticeDetailPresenterImpl(NoticeDetailView detailView, BaseActivity activity, int id) {
        mDetailView = detailView;
        mActivity = activity;
        mId = id;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getNoticeDetail() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY + mId) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getNoticeDetail(mId, cacheSubscriber);
    }

    private void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            Gson gson = new Gson();
            NoticeDetailBean bean = gson.fromJson(Goodo.toString(), NoticeDetailBean.class);
            mDetailView.getNoticeDetailBean(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
