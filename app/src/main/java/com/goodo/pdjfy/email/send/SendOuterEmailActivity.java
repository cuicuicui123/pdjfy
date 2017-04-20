package com.goodo.pdjfy.email.send;

import android.view.View;
import android.widget.Toast;

import com.goodo.pdjfy.email.InJavaScriptLocalObj;
import com.goodo.pdjfy.email.presenter.OuterMailToTrashPresenter;
import com.goodo.pdjfy.email.presenter.OuterMailToTrashPresenterImpl;
import com.goodo.pdjfy.email.presenter.SendOuterEmailPresenterImpl;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.MyConfig;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class SendOuterEmailActivity extends BaseSendOuterEmailActivity {
    private OuterMailToTrashPresenter mToTrashPresenter;

    @Override
    protected void handleArgument() {
        mOuterMailId = getIntent().getIntExtra(MyConfig.KEY_OUTER_MAIL_ID, 0);
        mSendOuterEmailBean.setOuterMailAddrId(mOuterMailId);
        mPresenter = new SendOuterEmailPresenterImpl(this, this);
        mToTrashTv.setVisibility(View.VISIBLE);
        mToTrashPresenter = new OuterMailToTrashPresenterImpl(this);
        mToTrashTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTrash();
            }
        });
        //给WebView一个高度，需要编辑，一行不容易点击到
        float minHeight = 80;
        String newContent = DataTransform.outEmailTransformHtmlToEditable(
                "<html><body style=\"background: white;width: 100%;min-height: " + minHeight +"px\"></body></html>");
        mWebView.loadData(newContent, "text/html; charset=UTF-8", null);
    }

    private void toTrash(){
        if (mTitleEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写标题！", Toast.LENGTH_SHORT).show();
            return;
        }
        mSendOuterEmailBean.setSubject(mTitleEdt.getText().toString());
        mSendOuterEmailBean.setToName(mReceiverEdt.getText().toString());
        mSendOuterEmailBean.setCcName(mCcEdt.getText().toString());
        mSendOuterEmailBean.setBccName(mBccEdt.getText().toString());
        mWebView.loadUrl("javascript:window.java_obj.getSource(" +
                "document.documentElement.innerHTML);");
        mObj.setOnHtmlGetListener(new InJavaScriptLocalObj.OnHtmlGetListener() {
            @Override
            public void onHtmlGet(String html) {
                mSendOuterEmailBean.setBody(DataTransform.outEmailRemoveContentEditable(html));
                mToTrashPresenter.toTrash(mPresenter.getAttachList(), mSendOuterEmailBean);            }
        });
    }

}
