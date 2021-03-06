package com.goodo.app.email.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.goodo.app.R;
import com.goodo.app.base.AppContext;
import com.goodo.app.email.model.EmailListBean;
import com.goodo.app.util.DataTransform;
import com.goodo.app.util.MyConfig;
import com.goodo.app.util.OnItemClickListener;

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
    private OnItemClickListener mOnDeleteClickListener;

    public EmailListRecyclerViewAdapter(List<EmailListBean> beanList) {
        mBeanList = beanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder = new MyViewHolder(mInflater.inflate(R.layout.recyclerview_email_list, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        EmailListBean bean = mBeanList.get(position);
        //from为空代表是发件箱
        boolean isInBox = bean.getFrom() == null;
        viewHolder.mNameTv.setText(isInBox ? bean.getTo() : bean.getFrom());
        boolean isRead = (bean.isIsRead() == MyConfig.IS_READ);
        viewHolder.mReadIv.setVisibility(isInBox && isRead ? View.VISIBLE : View.GONE);
        viewHolder.mSubjectTv.setText(bean.getSubject());
        viewHolder.mDateTv.setText(DataTransform.transformDateTimeNoSecond(bean.getDate()));
        viewHolder.mSwipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
        viewHolder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnDeleteClickListener != null) {
                    mOnDeleteClickListener.onItemClick(position);
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
        @BindView(R.id.swipe_layout)
        SwipeLayout mSwipeLayout;
        @BindView(R.id.tv_delete)
        TextView mDeleteTv;
        @BindView(R.id.iv_read)
        ImageView mReadIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSwipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            mSwipeLayout.addDrag(SwipeLayout.DragEdge.Right, mDeleteTv);

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnDeleteClickListener(OnItemClickListener onItemClickListener){
        mOnDeleteClickListener = onItemClickListener;
    }

}
