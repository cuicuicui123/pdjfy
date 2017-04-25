package com.goodo.app.announcement.presenter;

import com.goodo.app.announcement.model.AnnounceDetailBean;
import com.goodo.app.announcement.view.AnnounceDetailView;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.homepage.model.AttachBean;
import com.goodo.app.homepage.view.AttachView;
import com.goodo.app.rxjava.CacheSubscriber;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.util.JudgeIsJsonArray;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 公告详情页面逻辑实现类
 */

public class AnnounceDetailPresenterImpl implements AnnounceDetailPresenter {
    private AnnounceDetailView mDetailView;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;
    private AttachView mAttachView;

    private String KEY_ANNOUNCE_DETAIL = "getAnnounceDetail";

    public AnnounceDetailPresenterImpl(AnnounceDetailView detailView, BaseActivity activity, AttachView attachView) {
        mDetailView = detailView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
        mAttachView = attachView;
    }

    @Override
    public void getAnnounceDetail(String contentId) {
        CacheSubscriber subscriber = new CacheSubscriber(KEY_ANNOUNCE_DETAIL + contentId) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getAnnounceDetail(contentId, subscriber);
    }

    private void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            JSONObject Record = Goodo.getJSONObject("Record");
            final Gson gson = new Gson();
            AnnounceDetailBean bean = gson.fromJson(Record.toString(), AnnounceDetailBean.class);
            mDetailView.getAnnounceDetail(bean);
            if (!Record.isNull("Attach")) {
                getAttach(Record, gson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mDetailView.getHtmlAnnounceDetail(response);
        }
    }

    private void getAttach(JSONObject record, final Gson gson) throws JSONException {
        final List<AttachBean> list = new ArrayList<>();
        JudgeIsJsonArray.judge(record, "Attach", new JudgeIsJsonArray.OnJudged() {
            @Override
            public void judged(JSONObject jsonObject) throws JSONException {
                AttachBean attachBean = gson.fromJson(jsonObject.toString(), AttachBean.class);
                list.add(attachBean);
            }
        });
        mAttachView.getAttach(list);
    }

}
