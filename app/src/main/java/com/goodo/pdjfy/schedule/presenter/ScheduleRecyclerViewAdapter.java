package com.goodo.pdjfy.schedule.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.schedule.model.ScheduleBean;
import com.goodo.pdjfy.util.MyConfig;
import com.goodo.pdjfy.util.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class ScheduleRecyclerViewAdapter extends RecyclerView.Adapter{
    private List<ScheduleBean> mScheduleBeanList;
    private LayoutInflater mInflater;
    private AppContext mAppContext;
    private OnItemClickListener mOnItemClickListener;

    public ScheduleRecyclerViewAdapter(List<ScheduleBean> scheduleBeanList) {
        mScheduleBeanList = scheduleBeanList;
        mAppContext = AppContext.getInstance();
        mInflater = LayoutInflater.from(mAppContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.viewpager_item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        ScheduleBean bean = mScheduleBeanList.get(position);
        viewHolder.mTimeTv.setText(bean.getBeginTime());
        viewHolder.mWorkTv.setText(bean.getWork());
        switch (bean.getType()) {
            case MyConfig.SCHEDULE_TYPE_COLLEGE:
                viewHolder.mBacLl.setBackgroundResource(R.drawable.corner_week_red_bac);
                break;
            case MyConfig.SCHEDULE_TYPE_DEPART:
                viewHolder.mBacLl.setBackgroundResource(R.drawable.corner_week_blue_bac);
                break;
            case MyConfig.SCHEDULE_TYPE_PERSON:
                viewHolder.mBacLl.setBackgroundResource(R.drawable.corner_week_grey_bac);
                break;
            default:
                break;
        }
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
        return mScheduleBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_time)
        TextView mTimeTv;
        @BindView(R.id.tv_work)
        TextView mWorkTv;
        @BindView(R.id.ll_bac)
        LinearLayout mBacLl;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

}
