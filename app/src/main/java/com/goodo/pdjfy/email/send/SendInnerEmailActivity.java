package com.goodo.pdjfy.email.send;

import android.view.View;
import android.widget.Toast;

import com.goodo.pdjfy.email.InJavaScriptLocalObj;
import com.goodo.pdjfy.email.presenter.SendInnerPresenterImpl;
import com.goodo.pdjfy.email.presenter.ToTrashPresenter;
import com.goodo.pdjfy.email.presenter.ToTrashPresenterImpl;
import com.goodo.pdjfy.util.DataTransform;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class SendInnerEmailActivity extends BaseSendInnerEmailActivity {
    private ToTrashPresenter mToTrashPresenter;

    @Override
    protected void handleArgument() {
        mPresenter = new SendInnerPresenterImpl(this, this);
        mToTrashTv.setVisibility(View.VISIBLE);
        mToTrashPresenter = new ToTrashPresenterImpl(this);
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
                mToTrashPresenter.toTrash(mPresenter.getAttachList(), mBean);
            }
        });
    }
}
