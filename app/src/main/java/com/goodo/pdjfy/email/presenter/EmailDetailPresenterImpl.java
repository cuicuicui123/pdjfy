package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.view.EmailDetailView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailDetailPresenterImpl implements EmailDetailPresenter {
    private int mId;
    private int mIsInBox;
    private BaseActivity mActivity;
    private EmailDetailView mEmailDetailView;
    private HttpMethods mHttpMethods;

    private String KEY_EMAIL_DETAIL = "";

    public EmailDetailPresenterImpl(int id, int isInBox, BaseActivity activity, EmailDetailView emailDetailView) {
        mId = id;
        mIsInBox = isInBox;
        mActivity = activity;
        mEmailDetailView = emailDetailView;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getEmailDetail() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_EMAIL_DETAIL + mId) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getEmailDetail(mId, mIsInBox, cacheSubscriber);
    }

    private void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final Gson gson = new Gson();
            EmailDetailBean bean = gson.fromJson(Goodo.getString("R"), EmailDetailBean.class);
            mEmailDetailView.getEmailDetail(bean);
            JSONObject R = Goodo.getJSONObject("R");
            final List<EmailAttachBean> list = new ArrayList<>();
            JudgeIsJsonArray.judge(R, "Attach", new JudgeIsJsonArray.OnJudged() {
                @Override
                public void judged(JSONObject jsonObject) throws JSONException {
                    EmailAttachBean attachBean = gson.fromJson(jsonObject.toString(), EmailAttachBean.class);
                    list.add(attachBean);
                }
            });
            if (list.size() > 0) {
                mEmailDetailView.getEmailAttachList(list);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
