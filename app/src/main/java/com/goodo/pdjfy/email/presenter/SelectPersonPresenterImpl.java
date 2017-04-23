package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.UnitBean;
import com.goodo.pdjfy.email.model.UnitUserBean;
import com.goodo.pdjfy.email.view.SelectPersonView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.rxjava.MySubscriber;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.goodo.pdjfy.util.MyConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
    private List<UnitUserBean>[] mUserBeanLists;

    private String KEY_UNIT_INFO = "getUnitInfo";

    public SelectPersonPresenterImpl(SelectPersonView selectPersonView, BaseActivity activity) {
        mSelectPersonView = selectPersonView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
        mUnitBeanList = new ArrayList<>();
    }

    @Override
    public void getUnitInfo() {
//        MySubscriber subscriber = new MySubscriber() {
//            @Override
//            protected void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
//                    JSONObject Record = Goodo.getJSONObject("Record");
//                    final Gson gson = new Gson();
//                    JudgeIsJsonArray.judge(Record, "Record", new JudgeIsJsonArray.OnJudged() {
//                        @Override
//                        public void judged(JSONObject jsonObject) throws JSONException {
//                            UnitBean bean = gson.fromJson(jsonObject.toString(), UnitBean.class);
//                            mUnitBeanList.add(bean);
//                        }
//                    });
//                    mSize = mUnitBeanList.size();
//                    mUserBeanLists = new List[mSize];
//                    for (int i = 0;i < mSize;i ++) {
//                        UnitBean bean = mUnitBeanList.get(i);
//                        getUnitUser(bean.getID(), i);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        mHttpMethods.getUnitInfo(subscriber);
        getUser();
    }

    @Override
    public void getUnitUser(int id, final int position) {
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
                    mUserBeanLists[position] = list;
                    mNum ++;
                    if (mNum == mSize) {
                        mSelectPersonView.getUnitInfoList(mUnitBeanList, mUserBeanLists);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mHttpMethods.getUnitGroupUser(id, subscriber);
    }

    public void getUser(){
        Subscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                handleUserResponse(response);
            }
        };
        mHttpMethods.getHttpService().getUnitInfo(MyConfig.USER_ID, MyConfig.UNIT_ID, MyConfig.SESSION_ID)
                .flatMap(new Func1<ResponseBody, Observable<?>>() {
                    @Override
                    public Observable<?> call(ResponseBody responseBody) {
                        handleUnitResponse(responseBody);
                        return Observable.from(mUnitBeanList).flatMap(new Func1<UnitBean, Observable<?>>() {
                            @Override
                            public Observable<?> call(UnitBean unitBean) {
                                return mHttpMethods.getHttpService().getUnitUser(MyConfig.USER_ID, MyConfig.UNIT_ID,
                                MyConfig.SESSION_ID, unitBean.getID(), true);
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void handleUserResponse(String response) {
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
            mUserBeanLists[mNum] = list;
            mNum ++;
            if (mNum == mSize) {
                mSelectPersonView.getUnitInfoList(mUnitBeanList, mUserBeanLists);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleUnitResponse(ResponseBody responseBody) {
        try {
            String response = responseBody.string();
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
            mUserBeanLists = new List[mSize];
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
