package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.UnitBean;
import com.goodo.pdjfy.email.model.UnitUserBean;
import com.goodo.pdjfy.email.view.SelectPersonView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.rxjava.MySubscriber;
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

public class SelectPersonPresenterImpl implements SelectPersonPresenter {
    private SelectPersonView mSelectPersonView;
    private BaseActivity mActivity;
    private HttpMethods mHttpMethods;
    private int mSize;
    private int mNum = 0;
    private List<UnitBean> mUnitBeanList;
    private List<List<UnitUserBean>> mUserBeanList;

    private String KEY_UNIT_INFO = "getUnitInfo";

    public SelectPersonPresenterImpl(SelectPersonView selectPersonView, BaseActivity activity) {
        mSelectPersonView = selectPersonView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
        mUnitBeanList = new ArrayList<>();
        mUserBeanList = new ArrayList<>();
    }

    @Override
    public void getUnitInfo() {
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    JSONObject Record = Goodo.getJSONObject("Record");
                    final Gson gson = new Gson();
                    JudgeIsJsonArray.judge(Record, "Record", new JudgeIsJsonArray.OnJudged() {
                        @Override
                        public void judged(JSONObject jsonObject) throws JSONException {
                            UnitBean bean = gson.fromJson(jsonObject.toString(), UnitBean.class);
                            mUnitBeanList.add(bean);
                        }
                    });
                    mSize = mUnitBeanList.size();
                    for (int i = 0;i < mSize;i ++) {
                        UnitBean bean = mUnitBeanList.get(i);
                        getUnitUser(bean.getID());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mHttpMethods.getUnitInfo(subscriber);
    }

    @Override
    public void getUnitUser(int id) {
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    final List<UnitUserBean> list = new ArrayList<>();
                    final Gson gson = new Gson();
                    JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                        @Override
                        public void judged(JSONObject jsonObject) throws JSONException {
                            UnitUserBean bean = gson.fromJson(jsonObject.toString(), UnitUserBean.class);
                            list.add(bean);
                        }
                    });
                    mUserBeanList.add(list);
                    mNum ++;
                    if (mNum == mSize) {
                        mSelectPersonView.getUnitInfoList(mUnitBeanList, mUserBeanList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mHttpMethods.getUnitGroupUser(id, subscriber);
    }
}
