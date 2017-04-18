package com.goodo.pdjfy.email.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseFragment;
import com.goodo.pdjfy.email.model.EmailBean;
import com.goodo.pdjfy.email.model.InnerMailBean;
import com.goodo.pdjfy.email.model.OuterMailBean;
import com.goodo.pdjfy.email.send.SendInnerEmailActivity;
import com.goodo.pdjfy.email.view.EmailView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class EmailPresenterImpl implements EmailPresenter {
    private BaseFragment mFragment;
    private EmailView mEmailView;
    private HttpMethods mHttpMethods;

    private static String KEY_GET_OUTER_MAIL = "getOuterMail";
    private static String KEY_GET_INNER_RECEIVE_CLASSIFY = "getInnerReceiveClassify";

    public EmailPresenterImpl(BaseFragment fragment, EmailView emailView) {
        mFragment = fragment;
        mEmailView = emailView;
        mHttpMethods = HttpMethods.getInstance();

    }

    @Override
    public void getOuterMail() {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_GET_OUTER_MAIL) {
            @Override
            protected void getCache(String cacheData) {
                handleOuterResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleOuterResponse(response);
            }
        };
        mHttpMethods.getOuterMail(cacheSubscriber);
    }

    @Override
    public void getInnerReceiveClassify(final List<OuterMailBean> list) {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_GET_INNER_RECEIVE_CLASSIFY) {
            @Override
            protected void getCache(String cacheData) {
                handleInnerResponse(cacheData, list);
            }

            @Override
            protected void onResponse(String response) {
                handleInnerResponse(response, list);
            }
        };
        mHttpMethods.getInnerClassify(cacheSubscriber);
    }

    @Override
    public void startToSendEmailActivity() {
        Intent it = new Intent(mFragment.getContext(), SendInnerEmailActivity.class);
        mFragment.startActivity(it);
    }

    private void handleOuterResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final Gson gson = new Gson();
            final List<OuterMailBean> list = new ArrayList<>();
            JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                @Override
                public void judged(JSONObject jsonObject) throws JSONException {
                    OuterMailBean bean = gson.fromJson(jsonObject.toString(), OuterMailBean.class);
                    list.add(bean);
                }
            });
            getInnerReceiveClassify(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleInnerResponse(String response, List<OuterMailBean> outerMailBeanList){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final List<InnerMailBean> list = new ArrayList<>();
            final Gson gson = new Gson();
            JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                @Override
                public void judged(JSONObject jsonObject) throws JSONException {
                    InnerMailBean bean = gson.fromJson(jsonObject.toString(), InnerMailBean.class);
                    list.add(bean);
                }
            });
            handleList(outerMailBeanList, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleList(List<OuterMailBean> outerMailBeanList, List<InnerMailBean> innerMailBeanList){
        List<EmailBean> allList = new ArrayList<>();
        allList.add(getAllEmailBean());
        allList.add(getInnerEmail(innerMailBeanList));
        getOuterMail(outerMailBeanList, allList);
        mEmailView.getMailBeanList(allList);
    }

    private void getOuterMail(List<OuterMailBean> outerMailBeanList, List<EmailBean> allList) {
        int size = outerMailBeanList.size();
        for (int i = 0;i < size;i ++) {
            EmailBean emailBean = new EmailBean();
            emailBean.setId(outerMailBeanList.get(i).getOuterMailAddr_ID());
            emailBean.setTitle(outerMailBeanList.get(i).getOuterMailName());
            List<EmailBean.ChildEmailBean> list = new ArrayList<>();
            EmailBean.ChildEmailBean childEmailBeanReceive = new EmailBean.ChildEmailBean();
            childEmailBeanReceive.setContent("收件箱");
            childEmailBeanReceive.setPicRes(R.drawable.pic_outter_receive);
            list.add(childEmailBeanReceive);
            EmailBean.ChildEmailBean childEmailBeanSend = new EmailBean.ChildEmailBean();
            childEmailBeanSend.setContent("发件箱");
            childEmailBeanSend.setPicRes(R.drawable.pic_outter_send);
            list.add(childEmailBeanSend);
            emailBean.setList(list);
            allList.add(emailBean);
        }
    }

    @NonNull
    private EmailBean getInnerEmail(List<InnerMailBean> innerMailBeanList) {
        int size = innerMailBeanList.size();
        EmailBean emailBean = new EmailBean();
        emailBean.setTitle("内部电函");
        List<EmailBean.ChildEmailBean> list = new ArrayList<>();
        for (int i = 0;i < size;i ++) {
            EmailBean.ChildEmailBean childEmailBean = new EmailBean.ChildEmailBean();
            childEmailBean.setId(innerMailBeanList.get(i).getReceiveClassify_ID());
            childEmailBean.setContent(innerMailBeanList.get(i).getReceiveClassifyName());
            childEmailBean.setPicRes(R.drawable.pic_all_email_receive);
            list.add(childEmailBean);
        }
        EmailBean.ChildEmailBean childEmailBean = new EmailBean.ChildEmailBean();
        childEmailBean.setContent("发件箱");
        childEmailBean.setPicRes(R.drawable.pic_all_email_send);
        list.add(childEmailBean);
        emailBean.setList(list);
        return emailBean;
    }

    @NonNull
    private EmailBean getAllEmailBean() {
        EmailBean emailBean = new EmailBean();
        emailBean.setTitle("所有邮箱");
        String[] texts = new String[]{"收件箱", "发件箱", "草稿箱", "回收箱"};
        int[] picRes = new int[]{R.drawable.pic_all_email_receive, R.drawable.pic_all_email_send, R.drawable.pic_all_email_draft, R.drawable.pic_all_email_trash};
        List<EmailBean.ChildEmailBean> list = new ArrayList<>();
        for (int i = 0;i < 4;i ++) {
            EmailBean.ChildEmailBean childEmailBean = new EmailBean.ChildEmailBean();
            childEmailBean.setContent(texts[i]);
            childEmailBean.setPicRes(picRes[i]);
            list.add(childEmailBean);
        }
        emailBean.setList(list);
        return emailBean;
    }

}
