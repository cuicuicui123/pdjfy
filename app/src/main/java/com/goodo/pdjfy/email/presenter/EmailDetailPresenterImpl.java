package com.goodo.pdjfy.email.presenter;

import android.content.Intent;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.send.ReplyInnerEmailActivity;
import com.goodo.pdjfy.email.send.ReplyOuterEmailActivity;
import com.goodo.pdjfy.email.send.TransmitInnerEmailActivity;
import com.goodo.pdjfy.email.send.TransmitOuterEmailActivity;
import com.goodo.pdjfy.email.view.EmailDetailView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.goodo.pdjfy.util.MyConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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
    private EmailDetailBean mEmailDetailBean;
    private List<EmailAttachBean> mAttachBeanList;

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

    @Override
    public void startToTransmitActivity() {
        if (mEmailDetailBean != null) {
            Intent it = new Intent();
            if (mEmailDetailBean.getMail_ID() > 0) {
                it.setClass(mActivity, TransmitInnerEmailActivity.class);
            } else {
                it.setClass(mActivity, TransmitOuterEmailActivity.class);
            }
            it.putExtra(MyConfig.KEY_EMAIL_DETAIL_BEAN, mEmailDetailBean);
            it.putExtra(MyConfig.KEY_EMAIL_ATTACH_LIST, (Serializable) mAttachBeanList);
            it.putExtra(MyConfig.KEY_IS_INBOX, mIsInBox);
            mActivity.startActivity(it);
        }
    }

    @Override
    public void startToReplyActivity() {
        if (mEmailDetailBean != null) {
            Intent it = new Intent();
            if (mEmailDetailBean.getMail_ID() > 0) {
                it.setClass(mActivity, ReplyInnerEmailActivity.class);
            } else {
                it.setClass(mActivity, ReplyOuterEmailActivity.class);
            }
            it.putExtra(MyConfig.KEY_EMAIL_DETAIL_BEAN, mEmailDetailBean);
            it.putExtra(MyConfig.KEY_EMAIL_ATTACH_LIST, (Serializable) mAttachBeanList);
            it.putExtra(MyConfig.KEY_IS_INBOX, mIsInBox);
            mActivity.startActivity(it);
        }
    }

    private void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final Gson gson = new Gson();
            mEmailDetailBean = gson.fromJson(Goodo.getString("R"), EmailDetailBean.class);
            mEmailDetailView.getEmailDetail(mEmailDetailBean);
            mAttachBeanList = new ArrayList<>();
            if (!Goodo.isNull("R")) {
                JSONObject R = Goodo.getJSONObject("R");
                JudgeIsJsonArray.judge(R, "Attach", new JudgeIsJsonArray.OnJudged() {
                    @Override
                    public void judged(JSONObject jsonObject) throws JSONException {
                        EmailAttachBean attachBean = gson.fromJson(jsonObject.toString(), EmailAttachBean.class);
                        mAttachBeanList.add(attachBean);
                    }
                });
            }
            mEmailDetailView.getEmailAttachList(mAttachBeanList);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
