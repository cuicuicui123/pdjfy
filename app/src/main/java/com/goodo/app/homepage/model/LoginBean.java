package com.goodo.app.homepage.model;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class LoginBean {
    String mAccount;
    String mPwd;
    String mOnLineType;
    String mDeviceName;
    String mPlace;

    public String getAccount() {
        return mAccount;
    }

    public void setAccount(String account) {
        mAccount = account;
    }

    public String getPwd() {
        return mPwd;
    }

    public void setPwd(String pwd) {
        mPwd = pwd;
    }

    public String getOnLineType() {
        return mOnLineType;
    }

    public void setOnLineType(String onLineType) {
        mOnLineType = onLineType;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }
}
