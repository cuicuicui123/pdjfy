package com.goodo.pdjfy.email.model;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class SendOuterEmailBean {
    String mSubject;
    String mBody;
    String mToName;
    String mCcName;
    String mBccName;
    String mFileNames;
    String mBase64Data;
    int mEmailId;
    String mOriginAttachs;
    int mOuterMailAddrId;

    public SendOuterEmailBean() {
        mOriginAttachs = "";
    }

    public int getOuterMailAddrId() {
        return mOuterMailAddrId;
    }

    public void setOuterMailAddrId(int outerMailId) {
        mOuterMailAddrId = outerMailId;
    }

    public String getOriginAttachs() {
        return mOriginAttachs;
    }

    public void setOriginAttachs(String originAttachs) {
        mOriginAttachs = originAttachs;
    }

    public int getEmailId() {
        return mEmailId;
    }

    public void setEmailId(int emailId) {
        mEmailId = emailId;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getToName() {
        return mToName;
    }

    public void setToName(String toName) {
        mToName = toName;
    }

    public String getCcName() {
        return mCcName;
    }

    public void setCcName(String ccName) {
        mCcName = ccName;
    }

    public String getBccName() {
        return mBccName;
    }

    public void setBccName(String bccName) {
        mBccName = bccName;
    }

    public String getFileNames() {
        return mFileNames;
    }

    public void setFileNames(String fileNames) {
        mFileNames = fileNames;
    }

    public String getBase64Data() {
        return mBase64Data;
    }

    public void setBase64Data(String base64Data) {
        mBase64Data = base64Data;
    }

}
