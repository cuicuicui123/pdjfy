package com.goodo.app.document.presenter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.base.AppContext;
import com.goodo.app.base.BaseFragment;
import com.goodo.app.base.BaseRecyclerViewAdapter;
import com.goodo.app.document.ReceiveDocDetailActivity;
import com.goodo.app.document.model.ReceiveDocumentListBean;
import com.goodo.app.util.DataTransform;
import com.goodo.app.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class ReceiveRecyclerViewAdapter extends BaseRecyclerViewAdapter {
    private List<ReceiveDocumentListBean> mReceiveBeanList;
    private LayoutInflater mInflater;
    private AppContext mAppContext;
    private BaseFragment mFragment;

    public ReceiveRecyclerViewAdapter(List<ReceiveDocumentListBean> receiveBeanList, BaseFragment fragment) {
        mReceiveBeanList = receiveBeanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
        mFragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.recyclerview_document, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        ReceiveDocumentListBean bean = mReceiveBeanList.get(position);
        viewHolder.mTitleTv.setText(bean.getTitle());
        viewHolder.mDateTv.setText(DataTransform.transformDateTimeNoSecond(bean.getReceiveDate()));
        viewHolder.mNameTv.setText(bean.getUserName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(mAppContext, ReceiveDocDetailActivity.class);
                it.putExtra(MyConfig.KEY_ID, mReceiveBeanList.get(position).getReceiveId());
                mFragment.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReceiveBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView mTitleTv;
        @BindView(R.id.tv_name)
        TextView mNameTv;
        @BindView(R.id.tv_date)
        TextView mDateTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
