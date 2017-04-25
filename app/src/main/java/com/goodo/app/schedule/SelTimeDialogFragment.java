package com.goodo.app.schedule;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.goodo.app.R;
import com.goodo.app.base.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description 选择时间
 */

public class SelTimeDialogFragment extends BaseDialogFragment {
    @BindView(R.id.timePicker)
    TimePicker mTimePicker;
    @BindView(R.id.tv_cancel)
    TextView mCancelTv;
    @BindView(R.id.tv_sure)
    TextView mSureTv;

    private int mBaseHour = 9;
    private OnSelTimeListener mListener;

    @Override
    protected View getDialogView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_sel_time, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        mTimePicker.is24HourView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mTimePicker.setHour(mBaseHour);
        } else {
            mTimePicker.setCurrentHour(mBaseHour);
        }
    }

    @Override
    protected void initEvent() {
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSelTime(getTimeStr());
                }
                dismiss();
            }
        });
    }

    public interface OnSelTimeListener{
        void onSelTime(String time);
    }

    public void setOnSelTimeListener(OnSelTimeListener onSelTimeListener){
        mListener = onSelTimeListener;
    }

    private String getTimeStr(){
        int hour;
        int minute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = mTimePicker.getHour();
            minute = mTimePicker.getMinute();
        } else {
            hour = mTimePicker.getCurrentHour();
            minute = mTimePicker.getCurrentMinute();
        }
        return getTime(hour) + ":" + getTime(minute);
    }

    private String getTime(int time){
        return time < 10 ? "0" + time : time + "";
    }

}
