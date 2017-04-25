package com.goodo.app.schedule;

import android.content.Intent;

import com.goodo.app.R;
import com.goodo.app.schedule.presenter.AddSchedulePresenterImpl;
import com.goodo.app.util.MyConfig;
import com.goodo.app.util.MyDateFormat;

import java.util.Calendar;

/**
 * Created by Cui on 2017/4/15.
 *
 * @Description 添加日程
 */

public class AddScheduleActivity extends BaseAddScheduleActivity {

    @Override
    protected void getInfo() {
        Intent it = getIntent();
        getDate(it);
        getTime(it);
        mSelDateTv.setText("日期：" + mDate);
        mSelBeginTimeTv.setText("开始时间：" + mBeginTime);
        mSelEndTimeTv.setText("结束时间：" + mEndTime);
        mPresenter = new AddSchedulePresenterImpl(this);
    }

    /**
     * 长按进来获取日期
     * @param it
     */
    private void getDate(Intent it) {
        if (it.getStringExtra(MyConfig.KEY_DATE) != null) {
            mDate = it.getStringExtra(MyConfig.KEY_DATE);
        } else {
            mCalendar = Calendar.getInstance();
            mDateFormat = MyDateFormat.getDateFormat();
            mDate = mDateFormat.format(mCalendar.getTime()).split(" ")[0];
        }
    }

    /**
     * 长按进来获取时间
     * @param it
     */
    private void getTime(Intent it) {
        int type = it.getIntExtra(MyConfig.KEY_TIME, 0);
        switch (type) {
            case MyConfig.ALL_DAY:
                mBeginTime = MyConfig.BEGIN_TIME_HOLE_DAY;
                mEndTime = MyConfig.END_TIME_HOLE_DAY;
                mAddScheduleBean.setAllDay(MyConfig.IS_ALL_DAY);
                mRadioGroup.check(R.id.hole_day);
                break;
            case MyConfig.MORNING:
                mBeginTime = MyConfig.BEGIN_TIME_MORNING;
                mEndTime = MyConfig.END_TIME_MORNING;
                mAddScheduleBean.setAllDay(MyConfig.NOT_ALL_DAY);
                mRadioGroup.check(R.id.morning);
                break;
            case MyConfig.AFTERNOON:
                mBeginTime = MyConfig.BEGIN_TIME_AFTERNOON;
                mEndTime = MyConfig.END_TIME_AFTERNOON;
                mAddScheduleBean.setAllDay(MyConfig.NOT_ALL_DAY);
                mRadioGroup.check(R.id.afternoon);
                break;
            default:
                mBeginTime = MyConfig.BEGIN_TIME_HOLE_DAY;
                mEndTime = MyConfig.END_TIME_HOLE_DAY;
                mAddScheduleBean.setAllDay(MyConfig.IS_ALL_DAY);
                mRadioGroup.check(R.id.hole_day);
                break;
        }
    }
}
