package com.goodo.app.email;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.EmailAttachBean;
import com.goodo.app.email.model.EmailDetailBean;
import com.goodo.app.email.presenter.DeleteEmailPresenter;
import com.goodo.app.email.presenter.DeleteEmailPresenterImpl;
import com.goodo.app.email.presenter.EmailDetailPresenter;
import com.goodo.app.email.presenter.EmailDetailPresenterImpl;
import com.goodo.app.email.view.DeleteEmailView;
import com.goodo.app.email.view.EmailDetailView;
import com.goodo.app.homepage.presenter.DownLoadFilePresenter;
import com.goodo.app.homepage.presenter.DownLoadFilePresenterImpl;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.util.DataTransform;
import com.goodo.app.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailDetailActivity extends BaseActivity implements EmailDetailView, DeleteEmailView{
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_send_person)
    TextView mSendPersonTv;
    @BindView(R.id.tv_receive_person)
    TextView mReceivePersonTv;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_date)
    TextView mDateTv;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.ll_attach)
    LinearLayout mAttachLl;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;
    @BindView(R.id.rl_send)
    RelativeLayout mSendRl;
    @BindView(R.id.rl_receive)
    RelativeLayout mReceiveRl;
    @BindView(R.id.tv_receive_open)
    TextView mReceiveOpenTv;
    @BindView(R.id.tv_reply)
    TextView mReplyTv;
    @BindView(R.id.iv_transmit)
    ImageView mTransmitIv;
    @BindView(R.id.iv_delete)
    ImageView mDeleteIv;

    private EmailDetailPresenter mPresenter;
    private int mId;
    private int mIsInBox;
    private int mPosition;
    private boolean mIsOpen;
    private DownLoadFilePresenter mDownLoadFilePresenter;
    private EmailDetailBean mEmailDetailBean;
    private DeleteEmailPresenter mDeleteEmailPresenter;
    private int mIsDel = 1;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_email_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Intent it = getIntent();
        mId = it.getIntExtra(MyConfig.KEY_ID, 0);
        mIsInBox = it.getIntExtra(MyConfig.KEY_IS_INBOX, 0);
        mPosition = it.getIntExtra(MyConfig.KEY_POSITION, 0);
        mIsDel = it.getIntExtra(MyConfig.KEY_IS_DEL, MyConfig.DRAFT);
        mPresenter = new EmailDetailPresenterImpl(mId, mIsInBox, this, this);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(false);
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
        mReplyTv.setVisibility(mIsInBox == MyConfig.IS_INBOX ? View.VISIBLE : View.GONE);
        mDeleteEmailPresenter = new DeleteEmailPresenterImpl(this, this);
    }

    @Override
    protected void initEvent() {
        mPresenter.getEmailDetail();
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTransmitIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startToTransmitActivity();
            }
        });
        mReplyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startToReplyActivity();
            }
        });
        mDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteEmailPresenter.deleteEmail(mId, mIsInBox, mIsDel, mPosition);
            }
        });
    }

    @Override
    public void getEmailDetail(final EmailDetailBean bean) {
        mEmailDetailBean = bean;
        if (mIsInBox == MyConfig.IS_INBOX) {//收件箱显示发件人，发件箱显示收件人
            mReceiveRl.setVisibility(View.GONE);
            mSendRl.setVisibility(View.VISIBLE);
            mSendPersonTv.setText(mEmailDetailBean.getFrom());
        } else {
            String[] texts = bean.getTo().split(",");
            int len = texts.length;
            if (len >= 2) {
                final String text = texts[0] + "," + texts[1] + "..";
                mReceiveOpenTv.setVisibility(View.VISIBLE);
                mReceiveOpenTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mIsOpen) {
                            mReceivePersonTv.setText(text);
                            mReceiveOpenTv.setText("展开");
                        } else {
                            mReceivePersonTv.setText(bean.getFrom());
                            mReceiveOpenTv.setText("收起");
                        }
                        mIsOpen = !mIsOpen;
                    }
                });
                mReceivePersonTv.setText(text);
            } else {
                mReceivePersonTv.setText(bean.getTo());
            }
            mReceiveRl.setVisibility(View.VISIBLE);
            mSendRl.setVisibility(View.GONE);
        }
        if (mEmailDetailBean.getDate().equals("")) {
            mDateTv.setVisibility(View.GONE);
        } else {
            mDateTv.setText(DataTransform.getDateTimeStr(DataTransform.transformDateTimeNoSecond(bean.getDate())));
        }
        mTitleTv.setText(mEmailDetailBean.getSubject());
        mWebView.loadDataWithBaseURL(HttpMethods.BASE_URL, bean.getBody(), "text/html", "utf-8", null);
    }

    @Override
    public void getEmailAttachList(List<EmailAttachBean> list) {
        if (list.size() > 0) {
            mAttachLl.setVisibility(View.VISIBLE);
            mAttachTv.setText("附件：共" + list.size() + "个");
            mAddAttachLl.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);

            for (final EmailAttachBean bean : list) {
                final View attachView = inflater.inflate(R.layout.item_attach, mAddAttachLl, false);
                mAddAttachLl.addView(attachView);
                TextView fileTv = (TextView) attachView.findViewById(R.id.tv_attach);
                fileTv.setText(bean.getName());
                ImageView fileIv = (ImageView) attachView.findViewById(R.id.iv_attach);
                fileIv.setImageResource(MyConfig.getFilePictureByName(bean.getName()));
                final ProgressBar progressBar = (ProgressBar) attachView.findViewById(R.id.progressbar);
                attachView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        view.setEnabled(false);
                        mDownLoadFilePresenter.downLoadFile(getAttachUrl(bean), bean.getName(), progressBar, attachView, true, false);
                    }
                });
            }
        } else {
            mAttachLl.setVisibility(View.GONE);
        }
    }

    private String getAttachUrl(EmailAttachBean bean){
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(HttpMethods.BASE_URL + "EduPlate/MSGMail/InterfaceJson.asmx/File_Download");
        builder.appendQueryParameter("SessionID", MyConfig.SESSION_ID);
        builder.appendQueryParameter("User_ID", MyConfig.USER_ID + "");
        builder.appendQueryParameter("Mail_ID", mEmailDetailBean.getMail_ID() + "");
        builder.appendQueryParameter("IsInBox", mIsInBox + "");
        builder.appendQueryParameter("Attach_ID", bean.getID() + "");
        return builder.toString();
    }

    @Override
    public void onDeleteEmail(int position) {
        Intent it = new Intent();
        it.putExtra(MyConfig.KEY_POSITION, position);
        setResult(Activity.RESULT_OK, it);
        finish();
    }
}
