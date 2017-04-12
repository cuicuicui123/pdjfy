package com.goodo.pdjfy.homepage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.NewsListBean;
import com.goodo.pdjfy.homepage.presenter.NewsListPresenter;
import com.goodo.pdjfy.homepage.presenter.NewsListPresenterImpl;
import com.goodo.pdjfy.homepage.presenter.RecyclerViewAdapter;
import com.goodo.pdjfy.homepage.view.NewsListView;
import com.goodo.pdjfy.util.MyConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 首页新闻列表
 */

public class NewsListActivity extends BaseActivity implements NewsListView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.btn_enter)
    Button mEnterBtn;

    private NewsListPresenter mPresenter;
    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private int mPage = 1;
    private int mPageSize = 10;
    private String mKeyword = "";
    private String mNews;
    private String mTitle;

    private String[] mPaths = new String[]{"Get_Json_TeachNoticeContentSearchList", "Get_Json_NewsContentSearchList",
            "Get_Json_RevealContentSearchList", "Get_Json_WorkContentSearchList"};
    private String[] mTitles = new String[]{"教学通知", "新闻动态", "成果展示", "党建工作"};
    private List<NewsListBean> mBeanList;



    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.blue);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mBeanList = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(mBeanList);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter = new NewsListPresenterImpl(this, this);
        int position = getIntent().getIntExtra(MyConfig.KEY_POSITION, 0);
        mNews = mPaths[position];
        mTitle = mTitles[position];
        mTitleTv.setText(mTitle);
    }

    @Override
    protected void initEvent() {

        mPresenter.getNewsList(mNews, mPage, mPageSize, mKeyword);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                mPresenter.getNewsList(mNews, mPage, mPageSize, mKeyword);
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPage ++;
                mPresenter.getNewsList(mNews, mPage, mPageSize, mKeyword);
            }
        });
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                NewsListBean bean = mBeanList.get(position);
                mPresenter.startToDetailActivity(bean.getContents_ID(), mTitle);
            }
        });
        mEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startToLoginActivity();
            }
        });
    }

    @Override
    public void getNewsListBeanList(List<NewsListBean> list, boolean hasNewInfo, int dbSize) {
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
