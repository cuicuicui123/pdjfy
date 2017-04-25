package com.goodo.app.schedule.presenter;

import android.content.Intent;

import com.goodo.app.schedule.model.ScheduleBean;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public interface ScheduleDetailPresenter {
    void getScheduleDetail(ScheduleBean scheduleBean);
    void startToEditScheduleActivity();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void deleteSchedule();
}
