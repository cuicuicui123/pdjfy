package com.goodo.app.schedule.presenter;

import android.content.Intent;

import com.goodo.app.schedule.model.ScheduleBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface SchedulePresenter {
    void getScheduleList(String beginDay, String endDay);
    void getClickScheduleList(List<ScheduleBean> list, int position);
    void startToScheduleDetailActivity(ScheduleBean scheduleBean);
    void startToAddScheduleActivity();
    void startToAddScheduleActivity(String date, int time);//长按添加，要传入长按位置
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
