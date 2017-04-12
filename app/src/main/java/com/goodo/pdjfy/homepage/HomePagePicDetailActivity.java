package com.goodo.pdjfy.homepage;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.HomePageDetailBean;
import com.goodo.pdjfy.homepage.presenter.HomePageDetailPresenter;
import com.goodo.pdjfy.homepage.presenter.HomePagePicDetailPresenterImpl;
import com.goodo.pdjfy.homepage.view.HomePageNewsDetailView;
import com.goodo.pdjfy.rxjava.HttpMethods;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 首页图片详情，用webView展示
 */

public class HomePagePicDetailActivity extends BaseActivity implements HomePageNewsDetailView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.btn_enter)
    Button mEnterBtn;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.webView)
    WebView mWebView;

    private HomePageDetailPresenter mPresenter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_home_page_pic_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mPresenter = new HomePagePicDetailPresenterImpl(this, this);
        mTitleTv.setText(getIntent().getStringExtra(Flag.KEY_TITLE));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }

    @Override
    protected void initEvent() {
        mPresenter.getHomePagePicDetail(getIntent().getStringExtra(Flag.KEY_CONTENT_ID));
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
        mWebView.loadDataWithBaseURL(HttpMethods.BASE_URL, bean.getContent(), "text/html", "utf-8", "");
    }
}
