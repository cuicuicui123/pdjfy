package com.goodo.app.schedule.view;

import com.goodo.app.schedule.model.ScheduleBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface ScheduleView {
    void getScheduleList(List<ScheduleBean> list);
}
