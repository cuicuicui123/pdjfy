package com.goodo.pdjfy.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseDialogFragment;
import com.goodo.pdjfy.util.MyConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class MakeSureDialogFragment extends BaseDialogFragment {
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_content)
    TextView mContentTv;
    @BindView(R.id.tv_cancel)
    TextView mCancelTv;
    @BindView(R.id.tv_sure)
    TextView mSureTv;

    private OnClickSureListener mListener;

    @Override
    protected View getDialogView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.dialog_make_sure, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        if (getArguments().containsKey(MyConfig.KEY_TITLE)) {
            mTitleTv.setText(getArguments().getString(MyConfig.KEY_DIALOG_TITLE));
        }
        if (getArguments().containsKey(MyConfig.KEY_DIALOG_CONTENT)) {
            mContentTv.setText(getArguments().getString(MyConfig.KEY_DIALOG_CONTENT));
        }
    }

    @Override
    protected void initEvent() {
        mCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickSure();
                dismiss();
            }
        });

    }

    public interface OnClickSureListener{
        void onClickSure();
    }

    public void setOnClickSureListener(OnClickSureListener onClickSureListener){
        mListener = onClickSureListener;
    }

}
