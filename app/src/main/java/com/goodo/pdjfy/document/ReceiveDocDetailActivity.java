package com.goodo.pdjfy.document;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.document.model.DocDetailBean;
import com.goodo.pdjfy.document.presenter.DetailPresenter;
import com.goodo.pdjfy.document.presenter.ReceiveDocDetailPresenterImpl;
import com.goodo.pdjfy.document.view.DetailView;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenter;
import com.goodo.pdjfy.homepage.presenter.DownLoadFilePresenterImpl;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class ReceiveDocDetailActivity extends BaseActivity implements DetailView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.tv_user)
    TextView mUserTv;
    @BindView(R.id.tv_date)
    TextView mDateTv;
    @BindView(R.id.ll_attach)
    LinearLayout mAttachLl;
    @BindView(R.id.tv_attach)
    TextView mAttachTv;
    @BindView(R.id.ll_add_attach)
    LinearLayout mAddAttachLl;

    private DetailPresenter mPresenter;
    private int mId;
    private DownLoadFilePresenter mDownLoadFilePresenter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_receive_doc_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mId = getIntent().getIntExtra(MyConfig.KEY_ID, 0);
        mPresenter = new ReceiveDocDetailPresenterImpl(this, this, mId);
        mDownLoadFilePresenter = new DownLoadFilePresenterImpl(this);
    }

    @Override
    protected void initEvent() {
        mPresenter.getDetail();
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getDetailBean(final DocDetailBean bean) {
        mTitleTv.setText(bean.getTitle());
        mUserTv.setText(bean.getUserName());
        mDateTv.setText(DataTransform.transformDateTimeNoSecond(bean.getReceiveDate()));
        if (bean.getAttachList() != null && bean.getAttachList().size() > 0) {
            List<DocDetailBean.Attach> attachList = bean.getAttachList();
            mAttachLl.setVisibility(View.VISIBLE);
            mAttachTv.setText("附件：共" + attachList.size() + "个");
            mAddAttachLl.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(this);
            for (final DocDetailBean.Attach attach : attachList) {
                final View attachView = inflater.inflate(R.layout.item_attach, mAddAttachLl, false);
                mAddAttachLl.addView(attachView);
                TextView fileTv = (TextView) attachView.findViewById(R.id.tv_attach);
                fileTv.setText(attach.getOriginalFile());
                ImageView fileIv = (ImageView) attachView.findViewById(R.id.iv_attach);
                fileIv.setImageResource(MyConfig.getFilePictureByName(attach.getOriginalFile()));
                final ProgressBar progressBar = (ProgressBar) attachView.findViewById(R.id.progressbar);
                attachView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        view.setEnabled(false);
                        String url = getAttachUrl(attach, bean.getTitle());
                        mDownLoadFilePresenter.downLoadFile(url, MyConfig.getFileName(attach.getUrl()), progressBar, attachView, true, true);
                    }
                });
            }
        } else {
            mAttachLl.setVisibility(View.GONE);
        }
    }

    private String getAttachUrl(DocDetailBean.Attach attach, String title){
        Uri.Builder builder = new Uri.Builder();
        builder.encodedPath(HttpMethods.BASE_URL + "EduPlate/jfyPublicDocument/IPublicDocument.asmx/DocumentFile_Download");
        builder.appendQueryParameter("Title", title);
        builder.appendQueryParameter("File_ID", attach.getFileId() + "");
        return builder.toString();
    }
}
