package com.goodo.pdjfy.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
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
 * @Description
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

    private SchedulePresenter mPresenter;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_schedule, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mDateTv.setText(mScheduleMainView.getYearAndMonth());
        mPresenter = new SchedulePresenterImpl(this, (BaseActivity) getActivity());
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
    }

    @Override
    public void getScheduleList(List<ScheduleBean> list) {
        mScheduleMainView.setScheduleBeanList(list);
    }
}
