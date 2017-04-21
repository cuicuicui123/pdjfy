package com.goodo.pdjfy.document.model;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class SendDocumentListBean {
    String mTitle;
    String mSendDate;
    int mSendId;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSendDate() {
        return mSendDate;
    }

    public void setSendDate(String sendDate) {
        mSendDate = sendDate;
    }

    public int getSendId() {
        return mSendId;
    }

    public void setSendId(int sendId) {
        mSendId = sendId;
    }
}
