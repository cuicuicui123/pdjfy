package com.goodo.pdjfy.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.presenter.HomePagePresenterImpl;
import com.goodo.pdjfy.news.model.NewsListBean;
import com.goodo.pdjfy.news.presenter.NewsListPresenter;
import com.goodo.pdjfy.news.presenter.NewsListPresenterImpl;
import com.goodo.pdjfy.news.view.NewsListView;

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

    private NewsListPresenter mPresenter;

    private int mPage = 1;
    private int mPageSize = 10;
    private String mKeyword = "";

    private String[] mPaths = new String[]{"Get_Json_TeachNoticeContentSearchList", "Get_Json_NewsContentSearchList",
            "Get_Json_RevealContentSearchList", "Get_Json_WorkContentSearchList"};

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new NewsListPresenterImpl(this);
        int position = getIntent().getIntExtra(HomePagePresenterImpl.KEY_POSITION, 0);
        mPresenter.getNewsList(mPaths[position], mPage, mPageSize, mKeyword);
    }

    @Override
    protected void initEvent() {
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getNewsListBeanList(List<NewsListBean> list) {

    }
}
