package com.goodo.pdjfy.homepage;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.AttachBean;
import com.goodo.pdjfy.homepage.model.HomePageDetailBean;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenter;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenterImpl;
import com.goodo.pdjfy.homepage.presenter.HomePageDetailPresenter;
import com.goodo.pdjfy.homepage.presenter.HomePageNewsDetailPresenterImpl;
import com.goodo.pdjfy.homepage.view.HomePageNewsDetailView;
import com.goodo.pdjfy.homepage.view.AttachView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页新闻列表详情
 */

public class HomePageNewsDetailActivity extends BaseActivity implements HomePageNewsDetailView, AttachView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.btn_enter)
    Button mEnterBtn;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_content_title)
    TextView mContentTitleTv;
    @BindView(R.id.tv_inform)
    TextView mInformTv;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.ll_attach)
    LinearLayout mAttachLl;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;

    private HomePageDetailPresenter mPresenter;
    private DownLoadFilePresenter mDownLoadFilePresenter;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mDownLoadFilePresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.acitvity_home_page_news_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new HomePageNewsDetailPresenterImpl(this, this, this);
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
        mTitleTv.setText(getIntent().getStringExtra(MyConfig.KEY_TITLE));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }

    @Override
    protected void initEvent() {
        mPresenter.getHomePagePicDetail(getIntent().getStringExtra(MyConfig.KEY_CONTENT_ID));
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startToLoginActivity();
            }
        });
    }

    @Override
    public void getNewsDetailBean(HomePageDetailBean bean) {
        mContentTitleTv.setText(bean.getContentTitle());
        mInformTv.setText("发送人："+ bean.getUserName() + "  发布日期："+bean.getSubmitDate() +
                "  浏览次数："+bean.getICount());
        String html = MyConfig.handleImage(bean.getContent(), AppContext.getInstance().getWindowWidth());
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
                fileIv.setImageResource(MyConfig.getFilePictureByName(bean.getName()));
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
