package com.goodo.pdjfy.homepage.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.homepage.model.NewsListBean;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private List<NewsListBean> mBeanList;
    private AppContext mAppContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(List<NewsListBean> mBeanList) {
        this.mBeanList = mBeanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(mInflater.inflate(R.layout.recyclerview_news_list, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        NewsListBean bean = mBeanList.get(position);
        viewHolder.mTitleTv.setText("【" + bean.getSubjectName() + "】" + bean.getContentTitle());
        viewHolder.mSenderTv.setText("发布人：" + bean.getUserName());
        viewHolder.mDateTv.setText(bean.getSubmitDate());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        @BindView(R.id.tv_title)
        public TextView mTitleTv;
        @BindView(R.id.tv_sender)
        public TextView mSenderTv;
        @BindView(R.id.tv_date)
        public TextView mDateTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

}
