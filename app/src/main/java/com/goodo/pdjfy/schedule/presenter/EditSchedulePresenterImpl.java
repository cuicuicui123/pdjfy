package com.goodo.pdjfy.schedule.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.rxjava.MySubscriber;
import com.goodo.pdjfy.schedule.model.AddScheduleBean;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class EditSchedulePresenterImpl extends BaseAddSchedulePresenterImpl {

    public EditSchedulePresenterImpl(BaseActivity activity) {
        super(activity);
        mText = "编辑失败！";
    }

    @Override
    protected void sendRequest(AddScheduleBean bean, MySubscriber subscriber) {
        mHttpMethods.editSchedule(bean, subscriber);
    }
}
