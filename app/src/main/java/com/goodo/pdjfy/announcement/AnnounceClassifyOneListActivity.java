package com.goodo.pdjfy.announcement;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.announcement.model.AnnounceListBean;
import com.goodo.pdjfy.announcement.presenter.AnnounceClassifyOneListPresenter;
import com.goodo.pdjfy.announcement.presenter.AnnounceClassifyOneListPresenterImpl;
import com.goodo.pdjfy.announcement.presenter.AnnounceRecyclerViewAdapter;
import com.goodo.pdjfy.announcement.view.AnnounceClassifyOneListView;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.DividerItemDecoration;
import com.goodo.pdjfy.homepage.EndlessRecyclerOnScrollListener;
import com.goodo.pdjfy.util.MyConfig;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 某种公告分类对应的列表
 */

public class AnnounceClassifyOneListActivity extends BaseActivity implements AnnounceClassifyOneListView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private AnnounceClassifyOneListPresenter mPresenter;
    private String mId;
    private int mPage = 1;
    private int mPageSize = 10;
    private String mKeyword = "";

    private AnnounceRecyclerViewAdapter mAdapter;
    private List<AnnounceListBean> mBeanList;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_announce_classify_one_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mPresenter = new AnnounceClassifyOneListPresenterImpl(this, this);
        mId = getIntent().getStringExtra(MyConfig.KEY_ID);
        mBeanList = new ArrayList<>();
        mAdapter = new AnnounceRecyclerViewAdapter(mBeanList);
        mRecyclerView.setAdapter(mAdapter);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
    }

    @Override
    protected void initEvent() {
        mPresenter.getAnnounceClassifyOneList(mId, mPage, mPageSize, mKeyword);
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.getAnnounceClassifyOneList(mId, mPage, mPageSize, mKeyword);
            }
        });
        //上拉加载更多
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPage ++;
                mPresenter.getAnnounceClassifyOneList(mId, mPage, mPageSize, mKeyword);
            }
        });
        //recyclerView点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.startToDetailActivity(mBeanList.get(position).getContents_ID());
            }
        });
    }

    @Override
    public void getAnnounceClassifyOneList(List<AnnounceListBean> list, boolean hasNewInfo, int dbSize) {
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
    }
}
