package com.goodo.pdjfy.email.model;

import java.io.Serializable;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class UnitUserBean implements Serializable {

    /**
     * User_ID : 135
     * LoginID : yangjianrong
     * UserName : 杨建荣
     */

    private int User_ID;
    private String LoginID;
    private String UserName;
    private boolean mIsSelected;

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public String getLoginID() {
        return LoginID;
    }

    public void setLoginID(String LoginID) {
        this.LoginID = LoginID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
