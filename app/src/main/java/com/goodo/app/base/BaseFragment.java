package com.goodo.app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Cui on 2016/8/3.
 * fragment基类
 */
public abstract class BaseFragment extends Fragment {
    public FragmentActivity mActivity;

    /**
     * 获得上下文对象
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * fragment被重新创建时调用
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 获得页面布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        View view = initView(inflater);
        return view;
    }

    /**
     * 当Activity初始化之后可以在这里进行一些数据的初始化操作
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * 子类实现此抽象方法返回View进行展示
     * @param inflater
     * @return
     */
    public abstract View initView(LayoutInflater inflater);


    /**
     * 子类在此方法中实现数据的初始化
     */
    public abstract void initData();

    /**
     * 子类可以复写此方法初始化事件
     */
    protected abstract void initEvent();

}
