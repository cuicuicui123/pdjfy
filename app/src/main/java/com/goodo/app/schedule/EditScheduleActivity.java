package com.goodo.app.schedule;

import com.goodo.app.R;
import com.goodo.app.schedule.model.ScheduleDetailBean;
import com.goodo.app.schedule.presenter.EditSchedulePresenterImpl;
import com.goodo.app.util.DataTransform;
import com.goodo.app.util.MyConfig;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class EditScheduleActivity extends BaseAddScheduleActivity {

    @Override
    protected void getInfo() {
        ScheduleDetailBean bean = getIntent().getParcelableExtra(MyConfig.KEY_SCHEDULE_DETAIL_BEAN);
        mAddScheduleBean.setId(bean.getId());
        mDate = DataTransform.transform(bean.getDate().split(" ")[0]);
        mSelDateTv.setText(mDate);
        mBeginTime = bean.getBeginTime();
        mSelBeginTimeTv.setText("开始时间：" + mBeginTime);
        mEndTime = bean.getEndTime();
        mSelEndTimeTv.setText("结束时间：" + mEndTime);
        mWorkEdt.setText(bean.getWork());
        mContentEdt.setText(bean.getContent());
        mAddressEdt.setText(bean.getAddress() == null ? "" : bean.getAddress());
        if (bean.getIsAllDay() == MyConfig.IS_ALL_DAY) {
            mRadioGroup.check(R.id.hole_day);
            mAddScheduleBean.setAllDay(MyConfig.IS_ALL_DAY);
        } else {
            mAddScheduleBean.setAllDay(MyConfig.NOT_ALL_DAY);
            if (mBeginTime.equals(MyConfig.BEGIN_TIME_MORNING) && mEndTime.equals(MyConfig.END_TIME_MORNING)) {
                mRadioGroup.check(R.id.morning);
            } else if (mBeginTime.equals(MyConfig.BEGIN_TIME_AFTERNOON) && mEndTime.equals(MyConfig.END_TIME_AFTERNOON)) {
                mRadioGroup.check(R.id.afternoon);
            } else {
                mRadioGroup.check(R.id.custom);
                mSelEndTimeTv.setEnabled(true);
                mSelBeginTimeTv.setEnabled(true);
            }
        }
        mPresenter = new EditSchedulePresenterImpl(this);
    }
}
