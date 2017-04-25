package com.goodo.app.homepage.model;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class UserBean {

    /**
     * User_ID : 262
     * UserName : 测试主任
     * Nick : 测试主任
     * UserType_ID : 3
     * Organization_ID : 10
     * Unit_ID : 1065
     * UnitName : 浦东教育发展研究院
     * SessionID : 262:3:1823641197:0
     * Pro : 1
     */

    private int User_ID;
    private String UserName;
    private String Nick;
    private int UserType_ID;
    private int Organization_ID;
    private int Unit_ID;
    private String UnitName;
    private String SessionID;
    private String Pro;
    private int EID;

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getNick() {
        return Nick;
    }

    public void setNick(String Nick) {
        this.Nick = Nick;
    }

    public int getUserType_ID() {
        return UserType_ID;
    }

    public void setUserType_ID(int UserType_ID) {
        this.UserType_ID = UserType_ID;
    }

    public int getOrganization_ID() {
        return Organization_ID;
    }

    public void setOrganization_ID(int Organization_ID) {
        this.Organization_ID = Organization_ID;
    }

    public int getUnit_ID() {
        return Unit_ID;
    }

    public void setUnit_ID(int Unit_ID) {
        this.Unit_ID = Unit_ID;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String UnitName) {
        this.UnitName = UnitName;
    }

    public String getSessionID() {
        return SessionID;
    }

    public void setSessionID(String SessionID) {
        this.SessionID = SessionID;
    }

    public String getPro() {
        return Pro;
    }

    public void setPro(String Pro) {
        this.Pro = Pro;
    }

    public int getEID() {
        return EID;
    }

    public void setEID(int EID) {
        this.EID = EID;
    }
}
