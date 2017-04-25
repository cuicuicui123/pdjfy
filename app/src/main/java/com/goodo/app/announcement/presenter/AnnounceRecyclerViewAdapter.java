package com.goodo.app.announcement.presenter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.announcement.model.AnnounceListBean;
import com.goodo.app.base.AppContext;
import com.goodo.app.util.DataTransform;
import com.goodo.app.util.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class AnnounceRecyclerViewAdapter extends Adapter {
    private List<AnnounceListBean> mBeanList;
    private AppContext mAppContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public AnnounceRecyclerViewAdapter(List<AnnounceListBean> beanList) {
        mBeanList = beanList;
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
        AnnounceListBean bean = mBeanList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.mTitleTv.setText("【" + bean.getSubjectName() + "】" + bean.getContentTitle());
        myViewHolder.mSenderTv.setText("发布人：" + bean.getUserName());
        myViewHolder.mDateTv.setText(DataTransform.transformDateTimeNoSecond(bean.getSubmitDate()));
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
