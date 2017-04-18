package com.goodo.pdjfy.email.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.email.model.EmailListBean;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailListRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<EmailListBean> mBeanList;
    private AppContext mAppContext;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public EmailListRecyclerViewAdapter(List<EmailListBean> beanList) {
        mBeanList = beanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.recyclerview_email_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        EmailListBean bean = mBeanList.get(position);
        //from为空代表是发件箱
        viewHolder.mNameTv.setText(bean.getFrom() == null ? bean.getTo() : bean.getFrom());
        viewHolder.mSubjectTv.setText(bean.getSubject());
        viewHolder.mDateTv.setText(DataTransform.transformDateTimeNoSecond(bean.getDate()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        @BindView(R.id.tv_name)
        TextView mNameTv;
        @BindView(R.id.tv_subject)
        TextView mSubjectTv;
        @BindView(R.id.tv_date)
        TextView mDateTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

}
