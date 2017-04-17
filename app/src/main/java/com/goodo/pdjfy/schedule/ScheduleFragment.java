package com.goodo.pdjfy.schedule;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.main.BaseMainFragment;
import com.goodo.pdjfy.schedule.model.ScheduleBean;
import com.goodo.pdjfy.schedule.presenter.SchedulePresenter;
import com.goodo.pdjfy.schedule.presenter.SchedulePresenterImpl;
import com.goodo.pdjfy.schedule.view.ScheduleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 点击查看日程，长按添加日程带有日期和时间，点击添加按钮添加默认当天时间全天
 */

public class ScheduleFragment extends BaseMainFragment implements ScheduleView {
    @BindView(R.id.iv_menu)
    ImageView mMenuIv;
    @BindView(R.id.tv_date)
    TextView mDateTv;
    @BindView(R.id.iv_pre)
    ImageView mPreIv;
    @BindView(R.id.iv_next)
    ImageView mNextIv;
    @BindView(R.id.scheduleView)
    ScheduleMainView mScheduleMainView;
    @BindView(R.id.iv_add)
    ImageView mAddIv;

    private SchedulePresenter mPresenter;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_schedule, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mDateTv.setText(mScheduleMainView.getYearAndMonth());
        mPresenter = new SchedulePresenterImpl(this, this);
        mPresenter.getScheduleList(mScheduleMainView.getBeginDay(), mScheduleMainView.getEndDay());
    }

    @Override
    protected void initEvent() {
        mMenuIv.setOnClickListener(new OnMenuClickListener());
        mPreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateTv.setText(mScheduleMainView.clickPre());
                mPresenter.getScheduleList(mScheduleMainView.getBeginDay(), mScheduleMainView.getEndDay());
            }
        });
        mNextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDateTv.setText(mScheduleMainView.clickNext());
                mPresenter.getScheduleList(mScheduleMainView.getBeginDay(), mScheduleMainView.getEndDay());
            }
        });
        mScheduleMainView.setOnItemClickListener(new ScheduleMainView.OnItemClickListener() {
            @Override
            public void onItemClick(List<ScheduleBean> list, int indexY) {
                mPresenter.getClickScheduleList(list, indexY);
            }
        });
        //长按添加日程
        mScheduleMainView.setOnItemLongClickListener(new ScheduleMainView.OnItemLongClickListener() {
            @Override
            public void onItemLogClick(String date, int time) {
                mPresenter.startToAddScheduleActivity(date, time);
            }
        });
        mAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startToAddScheduleActivity();
            }
        });
    }

    @Override
    public void getScheduleList(List<ScheduleBean> list) {
        mScheduleMainView.setScheduleBeanList(list);
    }
}
