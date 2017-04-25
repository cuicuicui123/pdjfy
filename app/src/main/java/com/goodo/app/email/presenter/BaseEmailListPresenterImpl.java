package com.goodo.app.email.presenter;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.EmailListBean;
import com.goodo.app.email.view.EmailListView;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.util.JudgeIsJsonArray;
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

public abstract class BaseEmailListPresenterImpl implements EmailListPresenter {
    protected EmailListView mEmailListView;
    protected BaseActivity mActivity;
    protected HttpMethods mHttpMethods;

    protected String KEY_LIST;
    protected int mDbSize;
    protected int mPage;
    protected int mPageSize;

    public BaseEmailListPresenterImpl(EmailListView emailListView, BaseActivity activity) {
        mEmailListView = emailListView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    protected void handleResponse(String response, boolean hasNewInfo){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final List<EmailListBean> list = new ArrayList<>();
            final Gson gson = new Gson();
            JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                @Override
                public void judged(JSONObject jsonObject) throws JSONException {
                    EmailListBean bean = gson.fromJson(jsonObject.toString(), EmailListBean.class);
                    list.add(bean);
                }
            });
            mDbSize = hasNewInfo ? 0 : list.size();
            mEmailListView.getEmailListBeanList(list, hasNewInfo, mDbSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
