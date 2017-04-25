package com.goodo.app.email.send;

import android.view.View;
import android.widget.Toast;

import com.goodo.app.email.InJavaScriptLocalObj;
import com.goodo.app.email.presenter.SendInnerEmailPresenterImpl;
import com.goodo.app.email.presenter.InnerEmailToTrashPresenter;
import com.goodo.app.email.presenter.InnerEmailToTrashPresenterImpl;
import com.goodo.app.util.DataTransform;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class SendInnerEmailActivity extends BaseSendInnerEmailActivity {
    private InnerEmailToTrashPresenter mInnerEmailToTrashPresenter;

    @Override
    protected void handleArgument() {
        mPresenter = new SendInnerEmailPresenterImpl(this, this);
        mToTrashTv.setVisibility(View.VISIBLE);
        mInnerEmailToTrashPresenter = new InnerEmailToTrashPresenterImpl(this);
        mToTrashTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTrash();
            }
        });
        float minHeight = 80;
        String newContent = DataTransform.outEmailTransformHtmlToEditable("<html><body style=\"background: white;width: 100%;min-height: " + minHeight +"px\"></body></html>");
        mWebView.loadData(newContent, "text/html; charset=UTF-8", null);
    }

    private void toTrash(){
        if (mTitleEdt.getText().toString() == null || mTitleEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写标题！", Toast.LENGTH_SHORT).show();
            return;
        }
        mBean.setSubject(mTitleEdt.getText().toString());
        mWebView.loadUrl("javascript:window.java_obj.getSource(" +
                "document.documentElement.innerHTML);");
        //获取webView中的html
        mObj.setOnHtmlGetListener(new InJavaScriptLocalObj.OnHtmlGetListener() {
            @Override
            public void onHtmlGet(String html) {
                mBean.setBody(DataTransform.outEmailRemoveContentEditable(html));
                mPresenter.getReceivers(mBean);
                mInnerEmailToTrashPresenter.toTrash(mPresenter.getAttachList(), mBean);
            }
        });
    }
}
