package com.goodo.app.document.model;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class ReceiveDocumentListBean {
    String mTitle;
    String mReceiveDate;
    int mReceiveId;
    String mUserName;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getReceiveDate() {
        return mReceiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        mReceiveDate = receiveDate;
    }

    public int getReceiveId() {
        return mReceiveId;
    }

    public void setReceiveId(int receiveId) {
        mReceiveId = receiveId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }
}
