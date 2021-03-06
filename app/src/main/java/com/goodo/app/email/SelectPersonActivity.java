package com.goodo.app.email;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.UnitBean;
import com.goodo.app.email.model.UnitUserBean;
import com.goodo.app.email.presenter.ExpandableListViewAdapter;
import com.goodo.app.email.presenter.SelectPersonPresenter;
import com.goodo.app.email.presenter.SelectPersonPresenterImpl;
import com.goodo.app.email.view.SelectPersonView;
import com.goodo.app.util.MyConfig;

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
    private List<UnitUserBean>[] mUnitUserBeanLists;

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
    public void getUnitInfoList(List<UnitBean> unitBeanList, List<UnitUserBean>[] userBeanLists) {
        mUnitBeanList = unitBeanList;
        mUnitUserBeanLists = userBeanLists;
        mAdapter = new ExpandableListViewAdapter(unitBeanList, userBeanLists);
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setGroupIndicator(null);
        mAdapter.notifyDataSetChanged();
    }

    private List<UnitUserBean> getSelectUser() {
        List<UnitUserBean> userBeanList = new ArrayList<>();
        if (mUnitUserBeanLists != null) {
            int size1 = mUnitUserBeanLists.length;
            for (int i = 0; i < size1; i++) {
                List<UnitUserBean> list = mUnitUserBeanLists[i];
                int size2 = list.size();
                for (int j = 0; j < size2; j++) {
                    UnitUserBean unitUserBean = list.get(j);
                    if (unitUserBean.isSelected()) {
                        userBeanList.add(unitUserBean);
                    }
                }
            }

        }
        return userBeanList;
    }

}
