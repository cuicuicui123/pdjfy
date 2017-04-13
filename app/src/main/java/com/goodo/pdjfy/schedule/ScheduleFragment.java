package com.goodo.pdjfy.schedule;

import android.view.LayoutInflater;
import android.view.View;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.main.BaseMainFragment;

import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class ScheduleFragment extends BaseMainFragment {

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_schedule, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
