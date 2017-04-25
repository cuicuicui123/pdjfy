package com.goodo.app.notice;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.goodo.app.R;
import com.goodo.app.homepage.DividerItemDecoration;
import com.goodo.app.main.BaseMainFragment;
import com.goodo.app.notice.model.NoticeListBean;
import com.goodo.app.notice.presenter.NoticeListPresenter;
import com.goodo.app.notice.presenter.NoticeListPresenterImpl;
import com.goodo.app.notice.presenter.NoticeRecyclerViewAdapter;
import com.goodo.app.notice.view.NoticeListView;
import com.goodo.app.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class NoticeFragment extends BaseMainFragment implements NoticeListView {
    @BindView(R.id.iv_menu)
    ImageView mMenuIv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private NoticeListPresenter mPresenter;
    private NoticeRecyclerViewAdapter mAdapter;
    private List<NoticeListBean> mBeanList;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_notice, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mBeanList = new ArrayList<>();
        mAdapter = new NoticeRecyclerViewAdapter(mBeanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mPresenter = new NoticeListPresenterImpl(this, this);
    }

    @Override
    protected void initEvent() {
        mPresenter.getNoticeList();
        mMenuIv.setOnClickListener(new OnMenuClickListener());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNoticeList();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.startToNoticeDetailActivity(mBeanList.get(position).getNoticeID());
            }
        });
    }

    @Override
    public void getNoticeList(List<NoticeListBean> list) {
        mBeanList.clear();
        mBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
