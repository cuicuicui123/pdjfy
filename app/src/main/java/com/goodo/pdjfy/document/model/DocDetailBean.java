package com.goodo.pdjfy.document.model;

import java.util.List;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class DocDetailBean {
    String mTitle;
    String mUserName;
    String mReceiveDate;
    List<Attach> mAttachList;
    List<String> mReceiverList;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getReceiveDate() {
        return mReceiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        mReceiveDate = receiveDate;
    }

    public List<Attach> getAttachList() {
        return mAttachList;
    }

    public void setAttachList(List<Attach> attachList) {
        mAttachList = attachList;
    }

    public List<String> getReceiverList() {
        return mReceiverList;
    }

    public void setReceiverList(List<String> receiverList) {
        mReceiverList = receiverList;
    }

    public static class Attach{
        String mOriginalFile;
        String mUrl;
        int mFileId;

        public String getOriginalFile() {
            return mOriginalFile;
        }

        public void setOriginalFile(String originalFile) {
            mOriginalFile = originalFile;
        }

        public String getUrl() {
            return mUrl;
        }

        public void setUrl(String url) {
            mUrl = url;
        }

        public int getFileId() {
            return mFileId;
        }

        public void setFileId(int fileId) {
            mFileId = fileId;
        }
    }
}
