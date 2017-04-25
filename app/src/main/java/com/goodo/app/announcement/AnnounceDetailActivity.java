package com.goodo.app.announcement;

import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodo.app.R;
import com.goodo.app.announcement.model.AnnounceDetailBean;
import com.goodo.app.announcement.presenter.AnnounceDetailPresenter;
import com.goodo.app.announcement.presenter.AnnounceDetailPresenterImpl;
import com.goodo.app.announcement.view.AnnounceDetailView;
import com.goodo.app.base.BaseActivity;
import com.goodo.app.homepage.model.AttachBean;
import com.goodo.app.homepage.presenter.DownLoadFilePresenter;
import com.goodo.app.homepage.presenter.DownLoadFilePresenterImpl;
import com.goodo.app.homepage.view.AttachView;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 院级公告详情
 */

public class AnnounceDetailActivity extends BaseActivity implements AnnounceDetailView, AttachView {
    @BindView(R.id.tv_content_title)
    TextView mContentTitleTv;
    @BindView(R.id.tv_inform)
    TextView mInformTv;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.ll_content)
    LinearLayout mContentLl;
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;
    @BindView(R.id.ll_attach)
    LinearLayout mAttachLl;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;

    private AnnounceDetailPresenter mPresenter;
    private DownLoadFilePresenter mDownLoadFilePresenter;
    private String mContentId;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_announce_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new AnnounceDetailPresenterImpl(this, this, this);
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
        mContentId = getIntent().getStringExtra(MyConfig.KEY_CONTENT_ID);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    protected void initEvent() {
        mPresenter.getAnnounceDetail(mContentId);
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void getAnnounceDetail(AnnounceDetailBean bean) {
        mContentTitleTv.setText(bean.getContentTitle());
        mInformTv.setText("发布人：" + bean.getUserName() + "  发布日期" + bean.getSubmitDate()
                + "  浏览次数:" + bean.getICount());
        mWebView.loadDataWithBaseURL(HttpMethods.BASE_URL, bean.getContent(), "text/html", "utf-8", null);
    }

    @Override
    public void getHtmlAnnounceDetail(String html) {
        mContentLl.setVisibility(View.GONE);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.loadDataWithBaseURL(HttpMethods.BASE_URL, html, "text/html", "utf-8", null);
    }

    @Override
    public void getAttach(List<AttachBean> list) {
        if (list.size() > 0) {
            mAttachLl.setVisibility(View.VISIBLE);
            mAttachTv.setText("附件：共" + list.size() + "个");
            mAddAttachLl.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);

            for (final AttachBean bean : list) {
                final View attachView = inflater.inflate(R.layout.item_attach, mAddAttachLl, false);
                mAddAttachLl.addView(attachView);
                TextView fileTv = (TextView) attachView.findViewById(R.id.tv_attach);
                fileTv.setText(bean.getName());
                ImageView fileIv = (ImageView) attachView.findViewById(R.id.iv_attach);
                fileIv.setImageResource(MyConfig.getFilePictureByName(bean.getUrl()));
                final ProgressBar progressBar = (ProgressBar) attachView.findViewById(R.id.progressbar);
                attachView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        view.setEnabled(false);
                        mDownLoadFilePresenter.downLoadFile(bean.getUrl(), MyConfig.getFileName(bean.getUrl()), progressBar, attachView, false, false);
                    }
                });
            }
        } else {
            mAttachLl.setVisibility(View.GONE);
        }
    }
}
