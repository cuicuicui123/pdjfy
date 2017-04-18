package com.goodo.pdjfy.email.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailListBean;
import com.goodo.pdjfy.email.presenter.EmailListPresenter;
import com.goodo.pdjfy.email.presenter.EmailListRecyclerViewAdapter;
import com.goodo.pdjfy.email.view.EmailListView;
import com.goodo.pdjfy.homepage.DividerItemDecoration;
import com.goodo.pdjfy.homepage.EndlessRecyclerOnScrollListener;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public abstract class BaseEmailListActivity extends BaseActivity implements EmailListView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    protected EmailListPresenter mPresenter;
    protected int mPage = 1;
    protected int mPageSize = 10;
    protected EmailListRecyclerViewAdapter mAdapter;
    protected List<EmailListBean> mBeanList;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_email_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mBeanList = new ArrayList<>();
        mAdapter = new EmailListRecyclerViewAdapter(mBeanList);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        initEmailActivity();
    }

    @Override
    protected void initEvent() {
        mPresenter.getEmailList(mPage, mPageSize);
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPage ++;
                mPresenter.getEmailList(mPage, mPageSize);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.getEmailList(mPage, mPageSize);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                setOnItemClickEvent(position);
            }
        });
    }

    protected abstract void initEmailActivity();


    @Override
    public void getEmailListBeanList(List<EmailListBean> list, boolean hasNewInfo, int dbSize) {
        if (mPage == 1) {
            mBeanList.clear();
        }
        if (hasNewInfo) {
            int size = mBeanList.size();
            for (int i = 0;i < dbSize;i ++) {
                mBeanList.remove(size - 1 - i);
            }
        }
        mBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    protected abstract void setOnItemClickEvent(int position);

}
