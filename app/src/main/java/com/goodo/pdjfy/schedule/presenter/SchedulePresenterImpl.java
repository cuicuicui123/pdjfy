package com.goodo.pdjfy.schedule.presenter;

import android.content.Intent;
import android.os.Bundle;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.schedule.ScheduleDetailActivity;
import com.goodo.pdjfy.schedule.ScheduleDialogFragment;
import com.goodo.pdjfy.schedule.model.ScheduleBean;
import com.goodo.pdjfy.schedule.view.ScheduleView;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.goodo.pdjfy.util.MyConfig;
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
    private BaseActivity mActivity;
    private HttpMethods mHttpMethods;

    private String KEY_GET_SCHEDULE_LIST = "getScheduleList";

    public SchedulePresenterImpl(ScheduleView scheduleView, BaseActivity activity) {
        mScheduleView = scheduleView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();

    }

    @Override
    public void getScheduleList(String beginDay, String endDay) {
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
            if (scheduleBean.isIsAllDay()) {
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
        Intent it = new Intent(mActivity, ScheduleDetailActivity.class);
        it.putExtra(MyConfig.KEY_SCHEDULE_BEAN, scheduleBean);
        mActivity.startActivity(it);
    }

    private void showDialogFragment(List<ScheduleBean> list, int position) {
        ScheduleDialogFragment dialogFragment = new ScheduleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MyConfig.KEY_LIST, (Serializable) list);
        bundle.putInt(MyConfig.KEY_POSITION, position);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(mActivity.getSupportFragmentManager(), "dialog");
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
