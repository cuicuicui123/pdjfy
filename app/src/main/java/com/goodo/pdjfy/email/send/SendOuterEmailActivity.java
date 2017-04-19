package com.goodo.pdjfy.email.send;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.SendOuterEmailBean;
import com.goodo.pdjfy.email.presenter.SendOuterEmailPresenterImpl;
import com.goodo.pdjfy.email.presenter.SendOuterPresenter;
import com.goodo.pdjfy.email.view.SendOuterEmailView;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class SendOuterEmailActivity extends BaseActivity implements SendOuterEmailView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_sure)
    TextView mSureTv;
    @BindView(R.id.edt_receiver)
    EditText mReceiverEdt;
    @BindView(R.id.edt_cc)
    EditText mCcEdt;
    @BindView(R.id.edt_bcc)
    EditText mBccEdt;
    @BindView(R.id.edt_title)
    EditText mTitleEdt;
    @BindView(R.id.edt_content)
    EditText mContentEdt;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;

    private SendOuterPresenter mPresenter;
    private LayoutInflater mInflater;
    private int mOuterMailId;
    private String mOuterMailName;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_send_outer_email);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new SendOuterEmailPresenterImpl(this, this);
        mInflater = LayoutInflater.from(this);
        Map<String, Object> map = (Map<String, Object>) getIntent().getSerializableExtra(MyConfig.KEY_MAP);
        mOuterMailId = (int) map.get(MyConfig.KEY_ID);
        mOuterMailName = (String) map.get(MyConfig.KEY_NAME);
    }

    @Override
    protected void initEvent() {
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOuterEmail();
            }
        });
        mAttachTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selFile();
            }
        });
    }

    private void sendOuterEmail(){
        SendOuterEmailBean bean = new SendOuterEmailBean();
        bean.setId(mOuterMailId);
        if (mTitleEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写标题！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mReceiverEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写收件人地址", Toast.LENGTH_SHORT).show();
            return;
        }
        bean.setSubject(mTitleEdt.getText().toString());
        bean.setBody(mContentEdt.getText().toString());
        bean.setToName(mReceiverEdt.getText().toString());
        bean.setCcName(mCcEdt.getText().toString());
        bean.setBccName(mBccEdt.getText().toString());
        mPresenter.sendOuterEmail(bean);
    }

    @Override
    public void getSelAttach(final String path) {
        final View attachView = mInflater.inflate(R.layout.item_sel_attach, mAddAttachLl, false);
        mAddAttachLl.addView(attachView);
        TextView fileTv = (TextView) attachView.findViewById(R.id.tv_attach);
        fileTv.setText(MyConfig.getFileName(path));
        ImageView fileIv = (ImageView) attachView.findViewById(R.id.iv_attach);
        fileIv.setImageResource(MyConfig.getFilePictureByName(path));
        ImageView clearIv = (ImageView) attachView.findViewById(R.id.iv_clear);
        attachView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = IntentUtil.getFileIntent(new File(path));
                startActivity(it);
            }
        });
        clearIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddAttachLl.removeView(attachView);
                mPresenter.removeAttach(path);
            }
        });
    }
}
