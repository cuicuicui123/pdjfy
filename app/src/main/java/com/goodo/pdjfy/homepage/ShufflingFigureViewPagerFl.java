package com.goodo.pdjfy.homepage;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.goodo.pdjfy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 轮播图ViewPager
 */

public class ShufflingFigureViewPagerFl extends FrameLayout {
    private Context mContext;
    private ViewPager mViewPager;
    private LinearLayout mDotLl;

    private int mDotNum;//图片数量
    private int mOldPosition = 0;//初始位置0
    private List<View> mDotList;

    public ShufflingFigureViewPagerFl(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ShufflingFigureViewPagerFl(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShufflingFigureViewPagerFl(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        initViewPager();
    }

    private void initViewPager(){
        mViewPager = new ViewPager(mContext);
        addView(mViewPager, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置需要展示的图片
     * @param adapter 适配器
     */
    public void setAdapter(PagerAdapter adapter){
        mViewPager.setAdapter(adapter);
        mDotNum = adapter.getCount();
        mViewPager.setOffscreenPageLimit(mDotNum);
        if (mDotNum > 0) {
            initDots();
        }
        setPageChangeListener();
    }

    /**
     * 页面改变时监听
     */
    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDotList.get(mOldPosition).setBackgroundResource(R.drawable.dot_normal);
                mDotList.get(position).setBackgroundResource(R.drawable.dot_select);
                mOldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化dot，即显示当前是第几张图片的视图
     */
    private void initDots(){
        mDotList = new ArrayList<>();
        mDotLl = new LinearLayout(mContext);
        mDotLl.setWeightSum(mDotNum);
        int size = (int) getResources().getDimension(R.dimen.dot_size);
        int margin = (int) getResources().getDimension(R.dimen.margin_content);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                size, size);
        lp.weight = 1;
        lp.rightMargin = margin;
        for (int i = 0;i < mDotNum;i ++) {
            View view = new View(mContext);
            view.setBackgroundResource(R.drawable.dot_normal);
            mDotList.add(view);
            mDotLl.addView(view, lp);
        }
        mDotList.get(0).setBackgroundResource(R.drawable.dot_select);
        FrameLayout.LayoutParams flLp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flLp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        flLp.bottomMargin = margin;
        addView(mDotLl, flLp);
    }

    /**
     * 用于定时切换item
     * @param position 位置
     */
    public void setCurrentItem(int position){
        mViewPager.setCurrentItem(position);
    }

}
