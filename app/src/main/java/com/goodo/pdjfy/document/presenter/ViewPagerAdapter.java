package com.goodo.pdjfy.document.presenter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.base.BaseFragment;
import com.goodo.pdjfy.base.BaseRecyclerViewAdapter;
import com.goodo.pdjfy.homepage.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<List> mLists;
    private AppContext mAppContext;
    private LayoutInflater mInflater;
    private BaseFragment mFragment;
    private OnRefreshListener mOnRefreshListener;
    private List<SwipeRefreshLayout> mSwipeRefreshLayouts;

    public ViewPagerAdapter(List<List> list, BaseFragment fragment) {
        mLists = list;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
        mFragment = fragment;
        mSwipeRefreshLayouts = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.viewpager_document, container, false);
        container.addView(view);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        recyclerView.setLayoutManager(new LinearLayoutManager(mAppContext));
        BaseRecyclerViewAdapter adapter = position == 0 ?
                new ReceiveRecyclerViewAdapter(mLists.get(position), mFragment) :
                new SendRecyclerViewAdapter(mLists.get(position), mFragment);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh();
                }
            }
        });
        mSwipeRefreshLayouts.add(swipeRefreshLayout);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnRefreshListener{
        void onRefresh();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        mOnRefreshListener = onRefreshListener;
    }

    public void refreshComplete(){
        for (SwipeRefreshLayout swipeRefreshLayout : mSwipeRefreshLayouts) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }


}
