package com.goodo.app.schedule.presenter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.base.AppContext;
import com.goodo.app.schedule.model.ScheduleBean;
import com.goodo.app.util.MyConfig;

import java.util.List;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class SchedulePageAdapter extends PagerAdapter {
    private List<ScheduleBean> mScheduleBeanList;
    private AppContext mAppContext;
    private LayoutInflater mInflater;

    public SchedulePageAdapter(List<ScheduleBean> scheduleBeanList) {
        mScheduleBeanList = scheduleBeanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public int getCount() {
        return mScheduleBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.viewpager_item_schedule, container, false);
        TextView timeTv = (TextView) view.findViewById(R.id.tv_time);
        TextView workTv = (TextView) view.findViewById(R.id.tv_work);
        LinearLayout bacLl = (LinearLayout) view.findViewById(R.id.ll_bac);
        ScheduleBean scheduleBean = mScheduleBeanList.get(position);
        timeTv.setText(scheduleBean.getBeginTime());
        workTv.setText(scheduleBean.getWork());
        switch (scheduleBean.getType()) {
            case MyConfig.SCHEDULE_TYPE_COLLEGE:
                bacLl.setBackgroundResource(R.drawable.corner_week_red_bac);
                break;
            case MyConfig.SCHEDULE_TYPE_DEPART:
                bacLl.setBackgroundResource(R.drawable.corner_week_blue_bac);
                break;
            case MyConfig.SCHEDULE_TYPE_PERSON:
                bacLl.setBackgroundResource(R.drawable.corner_week_green_bac);
                break;
            default:
                break;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
