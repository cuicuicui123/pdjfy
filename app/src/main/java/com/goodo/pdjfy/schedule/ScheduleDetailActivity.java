package com.goodo.pdjfy.schedule;

import android.content.Intent;
import android.os.Bundle;
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
    @BindView(R.id.tv_edit)
    TextView mEdtTv;
    @BindView(R.id.tv_delete)
    TextView mDeleteTv;

    private ScheduleDetailPresenter mPresenter;
    private ScheduleBean mScheduleBean;
    private MakeSureDialogFragment mDialogFragment;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_schedule_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new ScheduleDetailPresenterImpl(this, this);
        mScheduleBean = (ScheduleBean) getIntent().getSerializableExtra(MyConfig.KEY_SCHEDULE_BEAN);
        int visibility = mScheduleBean.getType() == MyConfig.SCHEDULE_TYPE_PERSON ? View.VISIBLE : View.GONE;
        mEdtTv.setVisibility(visibility);
        mDeleteTv.setVisibility(visibility);
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
        mEdtTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startToEditScheduleActivity();
            }
        });
        mDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialogFragment();
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

    private void showDeleteDialogFragment(){
        mDialogFragment = new MakeSureDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MyConfig.KEY_DIALOG_TITLE, "删除日程");
        bundle.putString(MyConfig.KEY_DIALOG_CONTENT, "确定删除日程？");
        mDialogFragment.setArguments(bundle);
        mDialogFragment.show(getSupportFragmentManager(), "dialogFragment");
        mDialogFragment.setCancelable(true);
        mDialogFragment.setOnClickSureListener(new MakeSureDialogFragment.OnClickSureListener() {
            @Override
            public void onClickSure() {
                mPresenter.deleteSchedule();
            }
        });
    }


}
