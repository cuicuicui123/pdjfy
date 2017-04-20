package com.goodo.pdjfy.email.send;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.CustomWebViewClient;
import com.goodo.pdjfy.email.InJavaScriptLocalObj;
import com.goodo.pdjfy.email.model.SendOuterEmailBean;
import com.goodo.pdjfy.email.presenter.SendOuterPresenter;
import com.goodo.pdjfy.email.view.SendOuterEmailView;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public abstract class BaseSendOuterEmailActivity extends BaseActivity implements SendOuterEmailView {
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
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;
    @BindView(R.id.tv_to_trash)
    TextView mToTrashTv;

    protected SendOuterPresenter mPresenter;
    protected LayoutInflater mInflater;
    protected int mOuterMailId;
    protected SendOuterEmailBean mSendOuterEmailBean;
    protected InJavaScriptLocalObj mObj;
    protected CustomWebViewClient mWebViewClient;

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
        mInflater = LayoutInflater.from(this);
        mSendOuterEmailBean = new SendOuterEmailBean();
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mObj = new InJavaScriptLocalObj();
        mWebViewClient = new CustomWebViewClient();
        mWebView.setWebViewClient(mWebViewClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mObj = new InJavaScriptLocalObj();
        mWebView.addJavascriptInterface(mObj, "java_obj");
        handleArgument();
    }

    protected abstract void handleArgument();

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
        if (mTitleEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写标题！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mReceiverEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写收件人地址", Toast.LENGTH_SHORT).show();
            return;
        }
        mSendOuterEmailBean.setSubject(mTitleEdt.getText().toString());
        mSendOuterEmailBean.setToName(mReceiverEdt.getText().toString());
        mSendOuterEmailBean.setCcName(mCcEdt.getText().toString());
        mSendOuterEmailBean.setBccName(mBccEdt.getText().toString());
        mObj.setOnHtmlGetListener(new InJavaScriptLocalObj.OnHtmlGetListener() {
            @Override
            public void onHtmlGet(String html) {
                mSendOuterEmailBean.setBody(DataTransform.outEmailRemoveContentEditable(html));
                mPresenter.sendOuterEmail(mSendOuterEmailBean);
            }
        });
        mWebView.loadUrl("javascript:window.java_obj.getSource(" +
                "document.documentElement.innerHTML);");
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
