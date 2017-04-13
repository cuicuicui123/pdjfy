package com.goodo.pdjfy.announcement;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.announcement.model.AnnounceListBean;
import com.goodo.pdjfy.announcement.presenter.AnnouncePresenter;
import com.goodo.pdjfy.announcement.presenter.AnnouncePresenterImpl;
import com.goodo.pdjfy.announcement.presenter.AnnounceRecyclerViewAdapter;
import com.goodo.pdjfy.announcement.view.AnnounceView;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.DividerItemDecoration;
import com.goodo.pdjfy.homepage.EndlessRecyclerOnScrollListener;
import com.goodo.pdjfy.main.BaseMainFragment;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 院级公告
 */

public class AnnouncementFragment extends BaseMainFragment implements AnnounceView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.iv_menu)
    ImageView mMenuIv;
    @BindView(R.id.tv_more)
    TextView mMoreTv;

    private AnnouncePresenter mPresenter;
    private int mPage = 1;
    private int mPageSize = 10;
    private String mKeyword = "";

    private AnnounceRecyclerViewAdapter mAdapter;
    private List<AnnounceListBean> mBeanList;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_announcement, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mBeanList = new ArrayList<>();
        mAdapter = new AnnounceRecyclerViewAdapter(mBeanList);
        mRecyclerView.setAdapter(mAdapter);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mPresenter = new AnnouncePresenterImpl(this, (BaseActivity) getActivity());
    }

    @Override
    protected void initEvent() {
        mPresenter.getAnnounceList(mPage, mPageSize, mKeyword);
        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.getAnnounceList(mPage, mPageSize, mKeyword);
            }
        });
        //上拉加载更多
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPage ++;
                mPresenter.getAnnounceList(mPage, mPageSize, mKeyword);
            }
        });
        mMenuIv.setOnClickListener(new OnMenuClickListener());
        //recyclerView点击事件
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.startToDetailActivity(mBeanList.get(position).getContents_ID());
            }
        });
        mMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startToMoreActivity();
            }
        });
    }

    @Override
    public void getAnnounceList(List<AnnounceListBean> list, boolean hasNewInfo, int dbSize) {
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
}
