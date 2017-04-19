package com.goodo.pdjfy.email.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.util.MyConfig;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class SendDialogRecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Map<String, Object>> mDataList;
    private LayoutInflater mInflater;
    private AppContext mAppContext;
    private OnItemClickListener mOnItemClickListener;

    public SendDialogRecyclerViewAdapter(List<Map<String, Object>> dataList) {
        mDataList = dataList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.recyclerview_dialog_send_email, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.mTextView.setText((String) mDataList.get(position).get(MyConfig.KEY_NAME));
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
        return mDataList.size();
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
