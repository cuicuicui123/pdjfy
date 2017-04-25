package com.goodo.app.schedule.presenter;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.rxjava.MySubscriber;
import com.goodo.app.schedule.model.AddScheduleBean;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class AddSchedulePresenterImpl extends BaseAddSchedulePresenterImpl {
    public AddSchedulePresenterImpl(BaseActivity activity) {
        super(activity);
        mText = "添加失败！";
    }

    @Override
    protected void sendRequest(AddScheduleBean bean, MySubscriber subscriber) {
        mHttpMethods.addSchedule(bean, subscriber);
    }
}
