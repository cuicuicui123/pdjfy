package com.goodo.pdjfy.schedule.view;

import com.goodo.pdjfy.schedule.model.ScheduleBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface ScheduleView {
    void getScheduleList(List<ScheduleBean> list);
}
