package com.goodo.pdjfy.email;

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

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.presenter.EmailDetailPresenter;
import com.goodo.pdjfy.email.presenter.EmailDetailPresenterImpl;
import com.goodo.pdjfy.email.view.EmailDetailView;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenter;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenterImpl;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailDetailActivity extends BaseActivity implements EmailDetailView{
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

    private EmailDetailPresenter mPresenter;
    private int mId;
    private int mIsInBox;
    private boolean mIsOpen;
    private DownLoadFilePresenter mDownLoadFilePresenter;
    private EmailDetailBean mEmailDetailBean;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_email_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mId = getIntent().getIntExtra(MyConfig.KEY_ID, 0);
        mIsInBox = getIntent().getIntExtra(MyConfig.KEY_IS_INBOX, 0);
        mPresenter = new EmailDetailPresenterImpl(mId, mIsInBox, this, this);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(false);
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
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
        mTitleTv.setText(bean.getSubject());
        mDateTv.setText(DataTransform.getDateTimeStr(DataTransform.transformDateTimeNoSecond(bean.getDate())));
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
                        mDownLoadFilePresenter.downLoadFile(getAttachUrl(bean), bean.getName(), progressBar, attachView, true);
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

}
