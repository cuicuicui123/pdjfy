package com.goodo.pdjfy.email;

import android.app.Activity;
import android.content.Intent;
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
import com.goodo.pdjfy.util.MyConfig;

import java.io.Serializable;
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
                List<UnitUserBean> list = getSelectUser();
                if (list.size() > 0) {
                    Intent it = getIntent();
                    it.putExtra(MyConfig.KEY_SEND_LIST, (Serializable) list);
                    setResult(Activity.RESULT_OK, it);
                    finish();
                }
            }
        });
    }

    @Override
    public void getUnitInfoList(List<UnitBean> unitBeanList, List<List<UnitUserBean>> userBeanList) {
        mUnitBeanList.addAll(unitBeanList);
        mUnitUserBeanList.addAll(userBeanList);
        mAdapter.notifyDataSetChanged();
    }

    private List<UnitUserBean> getSelectUser(){
        List<UnitUserBean> userBeanList = new ArrayList<>();
        int size1 = mUnitUserBeanList.size();
        for (int i = 0;i < size1;i ++) {
            List<UnitUserBean> list = mUnitUserBeanList.get(i);
            int size2 = list.size();
            for (int j = 0;j < size2;j ++) {
                UnitUserBean unitUserBean = list.get(j);
                if (unitUserBean.isSelected()) {
                    userBeanList.add(unitUserBean);
                }
            }
        }
        return userBeanList;
    }

}
