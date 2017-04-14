package com.goodo.pdjfy.schedule.presenter;

import com.goodo.pdjfy.schedule.model.ScheduleBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface SchedulePresenter {
    void getScheduleList(String beginDay, String endDay);
    void getClickScheduleList(List<ScheduleBean> list, int position);
}
