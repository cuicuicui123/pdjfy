package com.goodo.app.email;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodo.app.R;
import com.goodo.app.base.BaseDialogFragment;
import com.goodo.app.email.presenter.SendDialogRecyclerViewAdapter;
import com.goodo.app.homepage.DividerItemDecoration;
import com.goodo.app.util.MyConfig;
import com.goodo.app.util.OnItemClickListener;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class SendEmailDialogFragment extends BaseDialogFragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SendDialogRecyclerViewAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;

    @Override
    protected View getDialogView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_send_email, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        List<Map<String, Object>> list = (List<Map<String, Object>>) getArguments().getSerializable(MyConfig.KEY_SEND_LIST);
        mAdapter = new SendDialogRecyclerViewAdapter(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                dismiss();
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

}
