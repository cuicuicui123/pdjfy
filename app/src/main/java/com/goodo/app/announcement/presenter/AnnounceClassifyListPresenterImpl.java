package com.goodo.app.announcement.presenter;

import android.content.Intent;

import com.goodo.app.announcement.AnnounceClassifyOneListActivity;
import com.goodo.app.announcement.model.AnnounceClassifyListBean;
import com.goodo.app.announcement.view.AnnounceClassifyListView;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.rxjava.CacheSubscriber;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.util.MyConfig;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class AnnounceClassifyListPresenterImpl implements AnnounceClassifyListPresenter {
    private AnnounceClassifyListView mView;
    private BaseActivity mActivity;
    private HttpMethods mHttpMethods;

    private String KEY_GET_ANNOUNCE_CLASSIFY_LIST = "getAnnounceClassifyList";

    public AnnounceClassifyListPresenterImpl(AnnounceClassifyListView view, BaseActivity activity) {
        mView = view;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getAnnounceClassifyList() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_GET_ANNOUNCE_CLASSIFY_LIST) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getAnnounceClassifyList(cacheSubscriber);
    }

    @Override
    public void startToAnnounceClassifyOnListActivity(String id) {
        Intent it = new Intent(mActivity, AnnounceClassifyOneListActivity.class);
        it.putExtra(MyConfig.KEY_ID, id);
        mActivity.startActivity(it);
    }

    private void handleResponse(String response){
        try {
            JSONArray jsonArray = new JSONArray(response);
            Gson gson = new Gson();
            List<AnnounceClassifyListBean> list = new ArrayList<>();
            int len = jsonArray.length();
            for (int i = 0;i < len;i ++) {
                String beanString = jsonArray.getString(i);
                AnnounceClassifyListBean bean = gson.fromJson(beanString, AnnounceClassifyListBean.class);
                list.add(bean);
            }
            mView.getAnnounceClassifyList(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
