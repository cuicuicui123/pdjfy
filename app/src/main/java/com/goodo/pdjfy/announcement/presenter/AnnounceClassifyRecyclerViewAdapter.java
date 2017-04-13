package com.goodo.pdjfy.announcement.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.announcement.model.AnnounceClassifyListBean;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class AnnounceClassifyRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<AnnounceClassifyListBean> mBeanList;
    private AppContext mAppContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public AnnounceClassifyRecyclerViewAdapter(List<AnnounceClassifyListBean> beanList) {
        mBeanList = beanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.reclcerview_announce_classify, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mTextView.setText("【" + mBeanList.get(position).getSName() + "】");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }


}
