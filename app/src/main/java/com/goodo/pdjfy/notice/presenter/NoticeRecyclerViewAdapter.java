package com.goodo.pdjfy.notice.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.base.BaseRecyclerViewAdapter;
import com.goodo.pdjfy.notice.model.NoticeListBean;
import com.goodo.pdjfy.util.DataTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class NoticeRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    private List<NoticeListBean> mBeanList;
    private AppContext mAppContext;
    private LayoutInflater mInflater;

    public NoticeRecyclerViewAdapter(List<NoticeListBean> beanList) {
        mBeanList = beanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.recyclerview_notice_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        NoticeListBean bean = mBeanList.get(position);
        viewHolder.mTitleTv.setText(bean.getNoticeTitle());
        viewHolder.mDateTv.setText(DataTransform.transformDateTimeNoSecond(bean.getSubmitDate()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
        viewHolder.mReadIv.setVisibility(bean.isState() ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView mTitleTv;
        @BindView(R.id.tv_date)
        TextView mDateTv;
        @BindView(R.id.iv_read)
        ImageView mReadIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
