package com.goodo.app.schedule.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.goodo.app.base.AppContext;
import com.goodo.app.base.BaseFragment;
import com.goodo.app.rxjava.CacheSubscriber;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.schedule.AddScheduleActivity;
import com.goodo.app.schedule.ScheduleDetailActivity;
import com.goodo.app.schedule.ScheduleDialogFragment;
import com.goodo.app.schedule.model.ScheduleBean;
import com.goodo.app.schedule.view.ScheduleView;
import com.goodo.app.util.JudgeIsJsonArray;
import com.goodo.app.util.MyConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class SchedulePresenterImpl implements SchedulePresenter {
    private ScheduleView mScheduleView;
    private BaseFragment mFragment;
    private HttpMethods mHttpMethods;

    private String KEY_GET_SCHEDULE_LIST = "getScheduleList";
    private String mBeginDay;
    private String mEndDay;
    private AppContext mAppContext;

    public SchedulePresenterImpl(ScheduleView scheduleView, BaseFragment fragment) {
        mScheduleView = scheduleView;
        mFragment = fragment;
        mHttpMethods = HttpMethods.getInstance();
        mAppContext = AppContext.getInstance();
    }

    @Override
    public void getScheduleList(String beginDay, String endDay) {
        mBeginDay = beginDay;
        mEndDay = endDay;
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_GET_SCHEDULE_LIST + beginDay + endDay) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getSchedule(MyConfig.SESSION_ID, MyConfig.USER_ID, MyConfig.UNIT_ID, beginDay, endDay, cacheSubscriber);
    }

    @Override
    public void getClickScheduleList(List<ScheduleBean> list, int position) {
        if (list.size() > 0) {
            ScheduleBean scheduleBean = list.get(0);
            if (scheduleBean.getIsAllDay() == 1) {
                if (list.size() > 1) {//全天大于1条展示弹出框
                    showDialogFragment(list, position);
                } else {
                    startToScheduleDetailActivity(scheduleBean);
                }
            } else {//上下午大于两条展示弹出框
                if (list.size() > 2) {
                    showDialogFragment(list, position);
                } else {
                    startToScheduleDetailActivity(list.get(position));
                }
            }
        }
    }

    @Override
    public void startToScheduleDetailActivity(ScheduleBean scheduleBean) {
        Intent it = new Intent(mAppContext, ScheduleDetailActivity.class);
        it.putExtra(MyConfig.KEY_SCHEDULE_BEAN, scheduleBean);
        mFragment.startActivityForResult(it, MyConfig.SCHEDULE_DETAIL_CODE);
    }

    @Override
    public void startToAddScheduleActivity() {
        Intent it = new Intent(mAppContext, AddScheduleActivity.class);
        mFragment.startActivityForResult(it, MyConfig.ADD_SCHEDULE_CODE);
    }

    @Override
    public void startToAddScheduleActivity(String date, int time) {
        Intent it = new Intent(mAppContext, AddScheduleActivity.class);
        it.putExtra(MyConfig.KEY_DATE, date);
        it.putExtra(MyConfig.KEY_TIME, time);
        mFragment.startActivityForResult(it, MyConfig.ADD_SCHEDULE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyConfig.ADD_SCHEDULE_CODE || requestCode == MyConfig.SCHEDULE_DETAIL_CODE) {
                getScheduleList(mBeginDay, mEndDay);
            }
        }
    }

    private void showDialogFragment(List<ScheduleBean> list, int position) {
        ScheduleDialogFragment dialogFragment = new ScheduleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyConfig.KEY_LIST, (Serializable) list);
        bundle.putInt(MyConfig.KEY_POSITION, position);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(mFragment.getFragmentManager(), "dialog");
        dialogFragment.setCancelable(true);
    }

    private void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final Gson gson = new Gson();
            final List<ScheduleBean> list = new ArrayList<>();
            JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                @Override
                public void judged(JSONObject jsonObject) throws JSONException {
                    ScheduleBean bean = gson.fromJson(jsonObject.toString(), ScheduleBean.class);
                    list.add(bean);
                }
            });
            mScheduleView.getScheduleList(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
