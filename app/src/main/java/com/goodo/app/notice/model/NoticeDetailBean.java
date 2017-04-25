package com.goodo.app.notice.model;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class NoticeDetailBean {

    /**
     * Notice_ID : 1293
     * title : CS
     * Content : CS
     * SubmitDate : 2017/3/6 11:39:20
     * PublishDate :
     * UserName : 浦东教发院管理员
     * ICount : 1
     * TotalCount : 1
     * SendUserName : 浦东教发院管理员
     * State : 1
     * IsReply : False
     */

    private int Notice_ID;
    private String title;
    private String Content;
    private String SubmitDate;
    private String PublishDate;
    private String UserName;
    private int ICount;
    private int TotalCount;
    private String SendUserName;
    private int State;
    private boolean IsReply;

    public int getNotice_ID() {
        return Notice_ID;
    }

    public void setNotice_ID(int Notice_ID) {
        this.Notice_ID = Notice_ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String SubmitDate) {
        this.SubmitDate = SubmitDate;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String PublishDate) {
        this.PublishDate = PublishDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public int getICount() {
        return ICount;
    }

    public void setICount(int ICount) {
        this.ICount = ICount;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public String getSendUserName() {
        return SendUserName;
    }

    public void setSendUserName(String SendUserName) {
        this.SendUserName = SendUserName;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public boolean isIsReply() {
        return IsReply;
    }

    public void setIsReply(boolean IsReply) {
        this.IsReply = IsReply;
    }
}
