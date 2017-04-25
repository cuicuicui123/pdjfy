package com.goodo.app.email;

import android.webkit.JavascriptInterface;

/**
 * Created by Cui on 2016/11/1.
 * 邮箱部分JavaScript对象，用于将webView中的html获取出来
 * 异步获取
 */

public class InJavaScriptLocalObj {
    private String html;
    private OnHtmlGetListener mListener;

    /**
     * 获取webView的html
     * @param html
     */
    @JavascriptInterface
    public void getSource(String html){
        this.html = html;
        if (mListener != null) {
            mListener.onHtmlGet(html);
        }
    }

    public String getHtml(){
        return html;
    }

    public interface OnHtmlGetListener{
        void onHtmlGet(String html);
    }

    public void setOnHtmlGetListener(OnHtmlGetListener onHtmlGetListener){
        mListener = onHtmlGetListener;
    }

}
