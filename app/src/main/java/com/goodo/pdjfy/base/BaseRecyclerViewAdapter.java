package com.goodo.pdjfy.base;

import android.support.v7.widget.RecyclerView;

import com.goodo.pdjfy.util.OnItemClickListener;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter {
    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
