package com.goodo.pdjfy.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.main.BaseMainFragment;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class ScheduleFragment extends BaseMainFragment {
    @BindView(R.id.scheduleView)
    ScheduleView mScheduleView;
    @BindView(R.id.iv_pre)
    ImageView mPreIv;
    @BindView(R.id.iv_next)
    ImageView mNextIv;
    @BindView(R.id.tv_date)
    TextView mDateTv;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_schedule, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mDateTv.setText(mScheduleView.getYearAndMonth());
    }

    @Override
    protected void initEvent() {
        mPreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScheduleView.clickLeft();
                mDateTv.setText(mScheduleView.getYearAndMonth());
            }
        });
        mNextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScheduleView.clickRight();
                mDateTv.setText(mScheduleView.getYearAndMonth());
            }
        });
    }
}
