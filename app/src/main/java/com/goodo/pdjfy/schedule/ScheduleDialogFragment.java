package com.goodo.pdjfy.schedule;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseDialogFragment;
import com.goodo.pdjfy.schedule.model.ScheduleBean;
import com.goodo.pdjfy.schedule.presenter.ScheduleRecyclerViewAdapter;
import com.goodo.pdjfy.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class ScheduleDialogFragment extends BaseDialogFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<ScheduleBean> mScheduleBeanList;
    private int mIndex;

    private ScheduleRecyclerViewAdapter mAdapter;

    @Override
    protected View getDialogView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_schedule, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mScheduleBeanList = (List<ScheduleBean>) bundle.getSerializable(MyConfig.KEY_LIST);
        mIndex = bundle.getInt(MyConfig.KEY_POSITION);
        setWidth(0.75);
        mAdapter = new ScheduleRecyclerViewAdapter(mScheduleBeanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        mRecyclerView.setAdapter(mAdapter);
    }



}
