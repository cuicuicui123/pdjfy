package com.goodo.pdjfy.document;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.document.model.ReceiveDocumentListBean;
import com.goodo.pdjfy.document.model.SendDocumentListBean;
import com.goodo.pdjfy.document.presenter.DocumentPresenter;
import com.goodo.pdjfy.document.presenter.DocumentPresenterImpl;
import com.goodo.pdjfy.document.presenter.ViewPagerAdapter;
import com.goodo.pdjfy.document.view.DocumentView;
import com.goodo.pdjfy.main.BaseMainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class DocumentFragment extends BaseMainFragment implements DocumentView {
    @BindView(R.id.iv_menu)
    ImageView mMenuIv;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private ViewPagerAdapter mAdapter;
    private DocumentPresenter mPresenter;
    private List<List> mLists;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_document, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mLists = new ArrayList<>();
        mAdapter = new ViewPagerAdapter(mLists, this);
        mViewPager.setAdapter(mAdapter);
        mPresenter = new DocumentPresenterImpl(this, this);
        mPresenter.getReceiveDocumentList();
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initEvent() {
        mMenuIv.setOnClickListener(new OnMenuClickListener());
        mAdapter.setOnRefreshListener(new ViewPagerAdapter.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getReceiveDocumentList();
            }
        });
    }

    @Override
    public void getDocumentList(List<ReceiveDocumentListBean> receiveBeanList, List<SendDocumentListBean> sendBeanList) {
        mLists.clear();
        mLists.add(receiveBeanList);
        mLists.add(sendBeanList);
        mAdapter.notifyDataSetChanged();
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText("接收公文");
        mTabLayout.getTabAt(1).setText("已发公文");
        mAdapter.refreshComplete();
    }



}
