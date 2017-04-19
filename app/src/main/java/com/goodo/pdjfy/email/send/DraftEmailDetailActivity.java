package com.goodo.pdjfy.email.send;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.presenter.DraftEmailDetailPresenterImpl;
import com.goodo.pdjfy.email.presenter.EmailDetailPresenter;
import com.goodo.pdjfy.email.presenter.EmailDetailPresenterImpl;
import com.goodo.pdjfy.email.view.EmailDetailView;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenter;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenterImpl;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.MyConfig;

import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class DraftEmailDetailActivity extends BaseSendInnerEmailActivity implements EmailDetailView {
    private EmailDetailPresenter mEmailDetailPresenter;
    private int mId;
    private int mIsInBox;
    private EmailDetailBean mEmailDetailBean;
    private DownLoadFilePresenter mDownLoadFilePresenter;

    @Override
    protected void handleArgument() {
        Intent it = getIntent();
        mId = it.getIntExtra(MyConfig.KEY_ID, 0);
        mIsInBox = it.getIntExtra(MyConfig.KEY_IS_INBOX, 0);
        mBean.setEmailId(mId);
        mEmailDetailPresenter = new EmailDetailPresenterImpl(mId, mIsInBox, this, this);
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
        mEmailDetailPresenter.getEmailDetail();
        mPresenter = new DraftEmailDetailPresenterImpl(this, this);
    }

    @Override
    public void getEmailDetail(EmailDetailBean bean) {
        mEmailDetailBean = bean;
    }

    @Override
    public void getEmailAttachList(final List<EmailAttachBean> list) {
        mPresenter.init(mEmailDetailBean, list);
        mTitleEdt.setText(mEmailDetailBean.getSubject());
        mContentEdt.setText(mEmailDetailBean.getBody());
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
