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
import com.goodo.pdjfy.email.presenter.EmailDetailPresenter;
import com.goodo.pdjfy.email.presenter.EmailDetailPresenterImpl;
import com.goodo.pdjfy.email.presenter.OuterEmailTrashPresenterImpl;
import com.goodo.pdjfy.email.view.EmailDetailView;
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

public class TrashOuterEmailDetailActivity extends BaseSendOuterEmailActivity implements EmailDetailView {
    private EmailDetailPresenter mEmailDetailPresenter;
    private int mId;
    private int mIsInBox;
    private DownLoadFilePresenter mDownLoadFilePresenter;
    private EmailDetailBean mEmailDetailBean;

    protected EmailHtmlHandleBean mHtmlHandleBean;

    @Override
    protected void handleArgument() {
        Intent it = getIntent();
        mId = it.getIntExtra(MyConfig.KEY_ID, 0);
        mIsInBox = it.getIntExtra(MyConfig.KEY_IS_INBOX, 0);
        mEmailDetailPresenter = new EmailDetailPresenterImpl(mId, mIsInBox, this, this);
        mSendOuterEmailBean.setEmailId(mId);
        mPresenter = new OuterEmailTrashPresenterImpl(this, this);
        mEmailDetailPresenter.getEmailDetail();
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
    }

    @Override
    public void getEmailDetail(EmailDetailBean bean) {
        mEmailDetailBean = bean;
        mSendOuterEmailBean.setOuterMailAddrId(mEmailDetailBean.getOuterMailAddr_ID());
        mReceiverEdt.setText(mEmailDetailBean.getTo());
        mCcEdt.setText(mEmailDetailBean.getCc());
        mBccEdt.setText(mEmailDetailBean.getBcc());
        mTitleEdt.setText(mEmailDetailBean.getSubject());

        mHtmlHandleBean = new EmailHtmlHandleBean();
        String newContent = mHtmlHandleBean.answerAndTransmitHtml(mEmailDetailBean);
        mWebView.loadData(newContent, "text/html; charset=UTF-8", null);
    }

    @Override
    public void getEmailAttachList(final List<EmailAttachBean> list) {
        mPresenter.init(mEmailDetailBean, list);

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
                        mDownLoadFilePresenter.downLoadFile(getAttachUrl(bean), bean.getName(), progressBar, attachView, true);
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
