package com.goodo.pdjfy.schedule;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseDialogFragment;
import com.goodo.pdjfy.util.MyDateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description 选择日程时间
 */

public class SelDateDialogFragment extends BaseDialogFragment {
    @BindView(R.id.datePicker)
    DatePicker mDatePicker;
    @BindView(R.id.tv_cancel)
    TextView mCancelTv;
    @BindView(R.id.tv_sure)
    TextView mSureTv;

    private Calendar mCalendar;
    private OnSelDateListener mListener;
    private SimpleDateFormat mDateFormat;

    @Override
    protected View getDialogView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_sel_date, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        mDateFormat = MyDateFormat.getDateFormat();
        mCalendar = Calendar.getInstance();
        mDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.i("date", year + "年" + monthOfYear + "月" + dayOfMonth + "日");
                    }
                });
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
                    mListener.onSelDate(getDateStr());
                }
                dismiss();
            }
        });
    }

    public interface OnSelDateListener{
        void onSelDate(String date);
    }

    public void setOnSelDateListener(OnSelDateListener onSelDateListener){
        mListener = onSelDateListener;
    }

    private String getDateStr(){
        mCalendar.set(Calendar.YEAR, mDatePicker.getYear());
        mCalendar.set(Calendar.MONTH, mDatePicker.getMonth());
        mCalendar.set(Calendar.DAY_OF_MONTH, mDatePicker.getDayOfMonth());
        return mDateFormat.format(mCalendar.getTime());
    }


}
