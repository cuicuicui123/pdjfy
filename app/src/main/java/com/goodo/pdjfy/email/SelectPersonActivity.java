package com.goodo.pdjfy.email;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.UnitBean;
import com.goodo.pdjfy.email.model.UnitUserBean;
import com.goodo.pdjfy.email.presenter.ExpandableListViewAdapter;
import com.goodo.pdjfy.email.presenter.SelectPersonPresenter;
import com.goodo.pdjfy.email.presenter.SelectPersonPresenterImpl;
import com.goodo.pdjfy.email.view.SelectPersonView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class SelectPersonActivity extends BaseActivity implements SelectPersonView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_sure)
    TextView mSureTv;
    @BindView(R.id.expandable_listView)
    ExpandableListView mExpandableListView;

    private SelectPersonPresenter mPresenter;
    private LayoutInflater mInflater;
    private ExpandableListViewAdapter mAdapter;
    private List<UnitBean> mUnitBeanList;
    private List<List<UnitUserBean>> mUnitUserBeanList;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_select_person);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new SelectPersonPresenterImpl(this, this);
        mPresenter.getUnitInfo();
        mInflater = LayoutInflater.from(this);
        mUnitBeanList = new ArrayList<>();
        mUnitUserBeanList = new ArrayList<>();
        mAdapter = new ExpandableListViewAdapter(mUnitBeanList, mUnitUserBeanList);
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setGroupIndicator(null);
    }

    @Override
    protected void initEvent() {
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void getUnitInfoList(List<UnitBean> unitBeanList, List<List<UnitUserBean>> userBeanList) {
        mUnitBeanList.addAll(unitBeanList);
        mUnitUserBeanList.addAll(userBeanList);
        mAdapter.notifyDataSetChanged();
    }
}
