package com.goodo.pdjfy.announcement.presenter;

import android.content.Intent;

import com.goodo.pdjfy.announcement.AnnounceDetailActivity;
import com.goodo.pdjfy.announcement.model.AnnounceListBean;
import com.goodo.pdjfy.announcement.view.AnnounceClassifyOneListView;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.goodo.pdjfy.util.MyConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class AnnounceClassifyOneListPresenterImpl implements AnnounceClassifyOneListPresenter {
    private AnnounceClassifyOneListView mView;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;

    private String KEY_GET_ANNOUNCE_CLASSIFY_ONE_LIST = "getAnnounceClassifyOneList";

    private int mDbSize;

    public AnnounceClassifyOneListPresenterImpl(AnnounceClassifyOneListView view, BaseActivity activity) {
        mView = view;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getAnnounceClassifyOneList(String id, int page, int pageSize, String keyword) {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_GET_ANNOUNCE_CLASSIFY_ONE_LIST + id + page) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData, false);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response, true);
            }
        };
        mHttpMethods.getAnnounceClassifyOneList(id, page, pageSize, keyword, cacheSubscriber);
    }

    @Override
    public void startToDetailActivity(String contentId) {
        Intent it = new Intent(mActivity, AnnounceDetailActivity.class);
        it.putExtra(MyConfig.KEY_CONTENT_ID, contentId);
        mActivity.startActivity(it);
    }

    private void handleResponse(String response, boolean hasNewInfo){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final Gson gson = new Gson();
            final List<AnnounceListBean> list = new ArrayList<>();
            JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                @Override
                public void judged(JSONObject jsonObject) throws JSONException {
                    AnnounceListBean bean = gson.fromJson(jsonObject.toString(), AnnounceListBean.class);
                    list.add(bean);
                }
            });
            mDbSize = hasNewInfo ? 0 : list.size();
            mView.getAnnounceClassifyOneList(list, hasNewInfo, mDbSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
