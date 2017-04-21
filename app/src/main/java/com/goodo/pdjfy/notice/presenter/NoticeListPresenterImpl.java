package com.goodo.pdjfy.notice.presenter;

import android.content.Intent;

import com.goodo.pdjfy.base.BaseFragment;
import com.goodo.pdjfy.notice.NoticeDetailActivity;
import com.goodo.pdjfy.notice.model.NoticeListBean;
import com.goodo.pdjfy.notice.view.NoticeListView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.MyConfig;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class NoticeListPresenterImpl implements NoticeListPresenter {
    private NoticeListView mView;
    private BaseFragment mFragment;
    private HttpMethods mHttpMethods;
    private String KEY = "getNoticeList";

    public NoticeListPresenterImpl(NoticeListView view, BaseFragment fragment) {
        mView = view;
        mFragment = fragment;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getNoticeList() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getNoticeList(cacheSubscriber);
    }

    @Override
    public void startToNoticeDetailActivity(int id) {
        Intent it = new Intent(mFragment.getContext(), NoticeDetailActivity.class);
        it.putExtra(MyConfig.KEY_ID, id);
        mFragment.startActivity(it);
    }

    private void handleResponse(String response){
        List<NoticeListBean> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            if (!response.startsWith("[")) {
                JSONObject jsonObject = new JSONObject(response);
                list.add(handleJsonObject(jsonObject, gson));
            } else {
                JSONArray jsonArray = new JSONArray(response);
                int len = jsonArray.length();
                for (int i = 0;i < len;i ++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    list.add(handleJsonObject(jsonObject, gson));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mView.getNoticeList(list);
    }

    private NoticeListBean handleJsonObject(JSONObject jsonObject, Gson gson){
        NoticeListBean bean = gson.fromJson(jsonObject.toString(), NoticeListBean.class);
        return bean;
    }

}
