package com.goodo.pdjfy.email;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.presenter.EmailDetailPresenter;
import com.goodo.pdjfy.email.presenter.EmailDetailPresenterImpl;
import com.goodo.pdjfy.email.view.EmailDetailView;
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

public class EmailDetailActivity extends BaseActivity implements EmailDetailView {
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

    private EmailDetailPresenter mPresenter;
    private int mId;
    private int mIsInBox;

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
    public void getEmailDetail(EmailDetailBean bean) {
        mReceivePersonTv.setText(bean.getTo());
        mSendPersonTv.setText(bean.getFrom());
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
//                        mDownLoadFilePresenter.downLoadFile(bean, progressBar, attachView);
                    }
                });
            }
        } else {
            mAttachLl.setVisibility(View.GONE);
        }
    }
}
