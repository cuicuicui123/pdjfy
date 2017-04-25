package com.goodo.app.email.list;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.EmailListBean;
import com.goodo.app.email.presenter.DeleteEmailPresenter;
import com.goodo.app.email.presenter.DeleteEmailPresenterImpl;
import com.goodo.app.email.presenter.EmailListPresenter;
import com.goodo.app.email.presenter.EmailListRecyclerViewAdapter;
import com.goodo.app.email.view.DeleteEmailView;
import com.goodo.app.email.view.EmailListView;
import com.goodo.app.homepage.DividerItemDecoration;
import com.goodo.app.homepage.EndlessRecyclerOnScrollListener;
import com.goodo.app.util.MyConfig;
import com.goodo.app.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public abstract class BaseEmailListActivity extends BaseActivity implements EmailListView, DeleteEmailView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    protected EmailListPresenter mPresenter;
    protected DeleteEmailPresenter mDeleteEmailPresenter;
    protected int mPage = 1;
    protected int mPageSize = 10;
    protected EmailListRecyclerViewAdapter mAdapter;
    protected List<EmailListBean> mBeanList;
    private LinearLayoutManager mLinearLayoutManager;
    protected int mIsInBox;
    protected int mIsDel = MyConfig.DRAFT;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyConfig.DETAIL_CODE) {
                int position = data.getIntExtra(MyConfig.KEY_POSITION, -1);
                if (position != -1 && position < mBeanList.size()) {
                    mBeanList.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_email_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mBeanList = new ArrayList<>();
        initEmailActivity();

        mAdapter = new EmailListRecyclerViewAdapter(mBeanList);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mDeleteEmailPresenter = new DeleteEmailPresenterImpl(this, this);
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
        mAdapter.setOnDeleteClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EmailListBean bean = mBeanList.get(position);
                int id = mIsInBox == MyConfig.IS_INBOX ? bean.getReceive_ID() : bean.getMail_ID();
                mDeleteEmailPresenter.deleteEmail(id, mIsInBox, mIsDel, position);
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

    @Override
    public void onDeleteEmail(int position) {
        mBeanList.remove(position);
        mAdapter.notifyDataSetChanged();
    }
}
