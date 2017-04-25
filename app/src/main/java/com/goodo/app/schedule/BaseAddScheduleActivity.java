package com.goodo.app.schedule;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goodo.app.R;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.schedule.model.AddScheduleBean;
import com.goodo.app.schedule.presenter.AddSchedulePresenter;
import com.goodo.app.util.MyConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public abstract class BaseAddScheduleActivity extends BaseActivity {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_sure)
    TextView mSureTv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_sel_date)
    TextView mSelDateTv;
    @BindView(R.id.radioGroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.tv_sel_begin_time)
    TextView mSelBeginTimeTv;
    @BindView(R.id.tv_sel_end_time)
    TextView mSelEndTimeTv;
    @BindView(R.id.edt_work)
    EditText mWorkEdt;
    @BindView(R.id.edt_address)
    EditText mAddressEdt;
    @BindView(R.id.edt_content)
    EditText mContentEdt;
    @BindView(R.id.ll_sel_time)
    LinearLayout mSelTimeLl;

    protected Calendar mCalendar;
    protected SimpleDateFormat mDateFormat;
    private SelDateDialogFragment mSelDateDialogFragment;
    private SelTimeDialogFragment mSelTimeDialogFragment;

    protected String mDate;
    protected String mBeginTime;
    protected String mEndTime;

    protected AddSchedulePresenter mPresenter;
    protected AddScheduleBean mAddScheduleBean;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_add_schedule);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mAddScheduleBean = new AddScheduleBean();
        getInfo();
    }

    @Override
    protected void initEvent() {
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSelDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelDateDialogFragment();
            }
        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                handleRadioGroup(checkedId);
            }
        });
        mSelBeginTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelTimeDialogFragment(new SelTimeDialogFragment.OnSelTimeListener() {
                    @Override
                    public void onSelTime(String time) {
                        mSelBeginTimeTv.setText("开始时间：" + time);
                        mBeginTime = time;
                    }
                });
            }
        });
        mSelEndTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelTimeDialogFragment(new SelTimeDialogFragment.OnSelTimeListener() {
                    @Override
                    public void onSelTime(String time) {
                        mSelEndTimeTv.setText("结束时间：" + time);
                        mEndTime = time;
                    }
                });
            }
        });
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddScheduleBean();
            }
        });
    }

    private void handleRadioGroup(@IdRes int checkedId) {
        if (checkedId == R.id.custom) {//自定义
            mSelBeginTimeTv.setEnabled(true);
            mSelEndTimeTv.setEnabled(true);
            mAddScheduleBean.setAllDay(MyConfig.NOT_ALL_DAY);
        } else {
            mSelBeginTimeTv.setEnabled(false);
            mSelEndTimeTv.setEnabled(false);
            switch (checkedId) {
                case R.id.hole_day:
                    mBeginTime = MyConfig.BEGIN_TIME_HOLE_DAY;
                    mEndTime = MyConfig.END_TIME_HOLE_DAY;
                    mAddScheduleBean.setAllDay(MyConfig.IS_ALL_DAY);
                    break;
                case R.id.morning:
                    mBeginTime = MyConfig.BEGIN_TIME_MORNING;
                    mEndTime = MyConfig.END_TIME_MORNING;
                    mAddScheduleBean.setAllDay(MyConfig.NOT_ALL_DAY);
                    break;
                case R.id.afternoon:
                    mBeginTime = MyConfig.BEGIN_TIME_AFTERNOON;
                    mEndTime = MyConfig.END_TIME_AFTERNOON;
                    mAddScheduleBean.setAllDay(MyConfig.NOT_ALL_DAY);
                    break;
                default:
                    break;
            }
            mSelBeginTimeTv.setText("开始时间：" + mBeginTime);
            mSelEndTimeTv.setText("结束时间：" + mEndTime);
        }
    }

    private void showSelDateDialogFragment(){
        mSelDateDialogFragment = new SelDateDialogFragment();
        mSelDateDialogFragment.show(getSupportFragmentManager(), "dialog");
        mSelDateDialogFragment.setCancelable(false);
        mSelDateDialogFragment.setOnSelDateListener(new SelDateDialogFragment.OnSelDateListener() {
            @Override
            public void onSelDate(String date) {
                mDate = date;
                mSelDateTv.setText("日期：" + date);
            }
        });
    }

    private void showSelTimeDialogFragment(SelTimeDialogFragment.OnSelTimeListener listener){
        mSelTimeDialogFragment = new SelTimeDialogFragment();
        mSelTimeDialogFragment.show(getSupportFragmentManager(), "dialog");
        mSelTimeDialogFragment.setCancelable(false);
        mSelTimeDialogFragment.setOnSelTimeListener(listener);
    }

    private void getAddScheduleBean(){
        if (!mWorkEdt.getText().toString().equals("")) {
            mAddScheduleBean.setWork(mWorkEdt.getText().toString());
        } else {
            Toast.makeText(this, "请填写工作标题！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mContentEdt.getText().toString().equals("")) {
            mAddScheduleBean.setContent(mContentEdt.getText().toString());
        } else {
            Toast.makeText(this, "请填写内容！", Toast.LENGTH_SHORT).show();
            return;
        }
        mAddScheduleBean.setDate(mDate);
        mAddScheduleBean.setBeginTime(mBeginTime);
        mAddScheduleBean.setEndTime(mEndTime);
        mAddScheduleBean.setAddress(mAddressEdt.getText().toString());
        mAddScheduleBean.setRelatedUser("1");
        mAddScheduleBean.setCaseId(0);
        mAddScheduleBean.setCaseName("无");
        mPresenter.addSchedule(mAddScheduleBean);
    }

    protected abstract void getInfo();

}
