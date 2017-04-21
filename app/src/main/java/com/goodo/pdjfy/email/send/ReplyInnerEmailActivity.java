package com.goodo.pdjfy.email.send;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.email.EmailHtmlHandleBean;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.presenter.ReplyInnerEmailPresenterImpl;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenter;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenterImpl;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.MyConfig;

import java.util.List;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class ReplyInnerEmailActivity extends BaseSendInnerEmailActivity {
    private EmailDetailBean mEmailDetailBean;
    private List<EmailAttachBean> mAttachBeanList;
    private int mIsInBox;
    private DownLoadFilePresenter mDownLoadFilePresenter;

    private EmailHtmlHandleBean mHtmlHandleBean;

    @Override
    protected void handleArgument() {
        Intent it = getIntent();
        mEmailDetailBean = it.getParcelableExtra(MyConfig.KEY_EMAIL_DETAIL_BEAN);
        mAttachBeanList = (List<EmailAttachBean>) it.getSerializableExtra(MyConfig.KEY_EMAIL_ATTACH_LIST);
        mIsInBox = it.getIntExtra(MyConfig.KEY_IS_INBOX, 0);
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
        mPresenter = new ReplyInnerEmailPresenterImpl(this, this);
        mPresenter.init(mEmailDetailBean, mAttachBeanList);
        getAttachView(mAttachBeanList);
        mTitleEdt.setText("Re：" + mEmailDetailBean.getSubject());
        mBean.setEmailId(mEmailDetailBean.getReceive_ID().equals("") ? 0 : Integer.parseInt(mEmailDetailBean.getReceive_ID()));

        mHtmlHandleBean = new EmailHtmlHandleBean();
        String newContent = mHtmlHandleBean.answerAndTransmitHtml(mEmailDetailBean);
        mWebView.loadData(newContent, "text/html; charset=UTF-8", null);
    }

    private void getAttachView(final List<EmailAttachBean> list){
        if (list.size() > 0) {
            LayoutInflater inflater = LayoutInflater.from(this);
            for (final EmailAttachBean bean : list) {
                final View attachView = inflater.inflate(R.layout.item_sel_attach, mAddAttachLl, false);
                mAddAttachLl.addView(attachView);
                TextView fileTv = (TextView) attachView.findViewById(R.id.tv_attach);
                fileTv.setText(bean.getName());
                ImageView fileIv = (ImageView) attachView.findViewById(R.id.iv_attach);
                fileIv.setImageResource(MyConfig.getFilePictureByName(bean.getName()));
                final ProgressBar progressBar = (ProgressBar) attachView.findViewById(R.id.progressbar);
                ImageView clearIv = (ImageView) attachView.findViewById(R.id.iv_clear);
                clearIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAddAttachLl.removeView(attachView);
                        list.remove(bean);
                    }
                });
                attachView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        view.setEnabled(false);
                        mDownLoadFilePresenter.downLoadFile(getAttachUrl(bean), bean.getName(), progressBar, attachView, true, false);
                    }
                });
            }
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
