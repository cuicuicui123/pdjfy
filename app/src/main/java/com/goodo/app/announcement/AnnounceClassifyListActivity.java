package com.goodo.app.announcement;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.goodo.app.R;
import com.goodo.app.announcement.model.AnnounceClassifyListBean;
import com.goodo.app.announcement.presenter.AnnounceClassifyListPresenter;
import com.goodo.app.announcement.presenter.AnnounceClassifyListPresenterImpl;
import com.goodo.app.announcement.presenter.AnnounceClassifyRecyclerViewAdapter;
import com.goodo.app.announcement.view.AnnounceClassifyListView;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.homepage.DividerItemDecoration;
import com.goodo.app.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 公告分类页面
 */

public class AnnounceClassifyListActivity extends BaseActivity implements AnnounceClassifyListView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private AnnounceClassifyListPresenter mPresenter;
    private AnnounceClassifyRecyclerViewAdapter mAdapter;
    private List<AnnounceClassifyListBean> mBeanList;


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_announce_classify_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new AnnounceClassifyListPresenterImpl(this, this);
        mBeanList = new ArrayList<>();
        mAdapter = new AnnounceClassifyRecyclerViewAdapter(mBeanList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        mPresenter.getAnnounceClassifyList();
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mPresenter.startToAnnounceClassifyOnListActivity(mBeanList.get(position).getSID());
            }
        });
    }

    @Override
    public void getAnnounceClassifyList(List<AnnounceClassifyListBean> list) {
        mBeanList.clear();
        mBeanList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }
}
