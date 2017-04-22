package com.goodo.pdjfy.email.send;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.CustomWebViewClient;
import com.goodo.pdjfy.email.InJavaScriptLocalObj;
import com.goodo.pdjfy.email.model.SendInnerEmailBean;
import com.goodo.pdjfy.email.model.UsersBean;
import com.goodo.pdjfy.email.presenter.SendInnerEmailPresenter;
import com.goodo.pdjfy.email.view.SendInnerEmailView;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public abstract class BaseSendInnerEmailActivity extends BaseActivity implements SendInnerEmailView {
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
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;
    @BindView(R.id.tv_to_trash)
    TextView mToTrashTv;

    protected SendInnerEmailPresenter mPresenter;
    protected LayoutInflater mInflater;
    protected SendInnerEmailBean mBean;
    private CustomWebViewClient mWebViewClient;
    protected InJavaScriptLocalObj mObj;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_send_inner_email);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mInflater = LayoutInflater.from(this);
        mBean = new SendInnerEmailBean();
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
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
        mSelReceiverIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selPerson(MyConfig.SEL_RECEIVER_CODE);
            }
        });
        mSelCcIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selPerson(MyConfig.SEL_CC_CODE);
            }
        });
        mSelBccIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selPerson(MyConfig.SEL_BCC_CODE);
            }
        });
        mAttachTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.selFile();
            }
        });
        mSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    @Override
    public void getSelReceiverPerson(UsersBean usersBean) {
        mSelReceiverTv.setText(usersBean.getNames());
    }

    @Override
    public void getSelCcPerson(UsersBean usersBean) {
        mSelCcTv.setText(usersBean.getNames());
    }

    @Override
    public void getSelBccPerson(UsersBean usersBean) {
        mSelBccTv.setText(usersBean.getNames());
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

    private void sendEmail(){
        if (mTitleEdt.getText().toString() == null || mTitleEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写标题！", Toast.LENGTH_SHORT).show();
            return;
        }

        mBean.setSubject(mTitleEdt.getText().toString());

        //获取webView中的html
        mObj.setOnHtmlGetListener(new InJavaScriptLocalObj.OnHtmlGetListener() {
            @Override
            public void onHtmlGet(String html) {
                mBean.setBody(DataTransform.outEmailRemoveContentEditable(html));
                mPresenter.sendInnerEmail(mBean);
            }
        });
        mWebView.loadUrl("javascript:window.java_obj.getSource(" +
                "document.documentElement.innerHTML);");
    }
}
