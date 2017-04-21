package com.goodo.pdjfy.notice;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.notice.model.NoticeDetailBean;
import com.goodo.pdjfy.notice.presenter.NoticeDetailPresenter;
import com.goodo.pdjfy.notice.presenter.NoticeDetailPresenterImpl;
import com.goodo.pdjfy.notice.view.NoticeDetailView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.MyConfig;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class NoticeDetailActivity extends BaseActivity implements NoticeDetailView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_info)
    TextView mInfoTv;
    @BindView(R.id.webView)
    WebView mWebView;

    private NoticeDetailPresenter mPresenter;
    private int mId;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mId = getIntent().getIntExtra(MyConfig.KEY_ID, 0);
        mPresenter = new NoticeDetailPresenterImpl(this, this, mId);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
    }

    @Override
    protected void initEvent() {
        mPresenter.getNoticeDetail();
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getNoticeDetailBean(NoticeDetailBean bean) {
        mTitleTv.setText(bean.getTitle());
        mInfoTv.setText("发送人：" + bean.getSendUserName() + "发送时间" + bean.getSubmitDate() + "浏览次数：" + bean.getTotalCount());
        mWebView.loadDataWithBaseURL(HttpMethods.BASE_URL, bean.getContent(), "text/html", "utf-8", null);
    }
}
