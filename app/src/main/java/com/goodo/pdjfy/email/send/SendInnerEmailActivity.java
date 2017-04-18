package com.goodo.pdjfy.email.send;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.SelectPersonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class SendInnerEmailActivity extends BaseActivity {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_sure)
    TextView mSureTv;
    @BindView(R.id.tv_sel_receiver)
    TextView mSelReceiverTv;
    @BindView(R.id.iv_sel_receiver)
    ImageView mSelReceiverIv;
    @BindView(R.id.rl_cc)
    RelativeLayout mCcRl;
    @BindView(R.id.tv_sel_cc)
    TextView mSelCcTv;
    @BindView(R.id.iv_sel_cc)
    ImageView mSelCcIv;
    @BindView(R.id.rl_bcc)
    RelativeLayout mBccRl;
    @BindView(R.id.tv_sel_bcc)
    TextView mSelBccTv;
    @BindView(R.id.iv_sel_bcc)
    ImageView mSelBccIv;
    @BindView(R.id.edt_title)
    EditText mTitleEdt;
    @BindView(R.id.edt_content)
    EditText mContentEdt;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_send_inner_email);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSelReceiverIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SendInnerEmailActivity.this, SelectPersonActivity.class);
                startActivity(it);
            }
        });
        mSelCcIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSelBccIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mAttachTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
