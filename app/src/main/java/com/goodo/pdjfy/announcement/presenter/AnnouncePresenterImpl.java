package com.goodo.pdjfy.announcement.presenter;

import android.content.Intent;

import com.goodo.pdjfy.announcement.AnnounceClassifyListActivity;
import com.goodo.pdjfy.announcement.AnnounceDetailActivity;
import com.goodo.pdjfy.announcement.model.AnnounceListBean;
import com.goodo.pdjfy.announcement.view.AnnounceView;
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

public class AnnouncePresenterImpl implements AnnouncePresenter {
    private AnnounceView mAnnounceView;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;

    private String KEY_ANNOUNCE_LIST = "getAnnounceList";
    private int mDbSize;

    public AnnouncePresenterImpl(AnnounceView announceView, BaseActivity activity) {
        mAnnounceView = announceView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getAnnounceList(int page, int pageSize, String keyword) {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_ANNOUNCE_LIST + page) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData, false);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response, true);
            }
        };
        mHttpMethods.getAnnounceList(page, pageSize, keyword, cacheSubscriber);
    }

    @Override
    public void startToDetailActivity(String contentId) {
        Intent it = new Intent(mActivity, AnnounceDetailActivity.class);
        it.putExtra(MyConfig.KEY_CONTENT_ID, contentId);
        mActivity.startActivity(it);
    }

    @Override
    public void startToMoreActivity() {
        mActivity.startActivity(new Intent(mActivity, AnnounceClassifyListActivity.class));
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
            mAnnounceView.getAnnounceList(list, hasNewInfo, mDbSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
