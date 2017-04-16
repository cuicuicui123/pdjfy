package com.goodo.pdjfy.schedule;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.schedule.model.ScheduleBean;
import com.goodo.pdjfy.schedule.model.ScheduleDetailBean;
import com.goodo.pdjfy.schedule.presenter.ScheduleDetailPresenter;
import com.goodo.pdjfy.schedule.presenter.ScheduleDetailPresenterImpl;
import com.goodo.pdjfy.schedule.view.ScheduleDetailView;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.MyConfig;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class ScheduleDetailActivity extends BaseActivity implements ScheduleDetailView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_work)
    TextView mWorkTv;
    @BindView(R.id.tv_date)
    TextView mDateTv;
    @BindView(R.id.tv_address)
    TextView mAddressTv;
    @BindView(R.id.tv_content)
    TextView mContentTv;

    private ScheduleDetailPresenter mPresenter;
    private ScheduleBean mScheduleBean;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_schedule_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new ScheduleDetailPresenterImpl(this, this);
        mScheduleBean = (ScheduleBean) getIntent().getSerializableExtra(MyConfig.KEY_SCHEDULE_BEAN);
    }

    @Override
    protected void initEvent() {
        mPresenter.getScheduleDetail(mScheduleBean);
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getScheduleDetailBean(ScheduleDetailBean bean) {
        mWorkTv.setText(bean.getWork());
        if (bean.getDate() != null && !bean.getDate().equals("")) {
            mDateTv.setText("时间：" + DataTransform.getDateStr(bean.getDate().split(" ")[0]) + " " + bean.getBeginTime());
        } else {
            mDateTv.setVisibility(View.GONE);
        }
        if (bean.getAddress() != null && bean.getAddress().equals("")) {
            mAddressTv.setVisibility(View.GONE);
        } else {
            mAddressTv.setVisibility(View.VISIBLE);
            mAddressTv.setText("地点：" + bean.getAddress());
        }
        if (bean.getContent() == null || bean.getContent().equals("")) {
            mContentTv.setVisibility(View.GONE);
        } else {
            mContentTv.setVisibility(View.VISIBLE);
            mContentTv.setText("内容：" + bean.getContent());
        }
    }
}
