package com.goodo.pdjfy.notice.model;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public class NoticeListBean {

    /**
     * NoticeID : 1293
     * NoticeTitle : CS
     * NotifyType : null
     * PublishDate : null
     * SubmitDate : 2017/3/6 11:39:20
     * UserName : 浦东教发院管理员
     * State : true
     * IsReply : false
     */

    private int NoticeID;
    private String NoticeTitle;
    private Object NotifyType;
    private Object PublishDate;
    private String SubmitDate;
    private String UserName;
    private boolean State;
    private boolean IsReply;

    public int getNoticeID() {
        return NoticeID;
    }

    public void setNoticeID(int NoticeID) {
        this.NoticeID = NoticeID;
    }

    public String getNoticeTitle() {
        return NoticeTitle;
    }

    public void setNoticeTitle(String NoticeTitle) {
        this.NoticeTitle = NoticeTitle;
    }

    public Object getNotifyType() {
        return NotifyType;
    }

    public void setNotifyType(Object NotifyType) {
        this.NotifyType = NotifyType;
    }

    public Object getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(Object PublishDate) {
        this.PublishDate = PublishDate;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String SubmitDate) {
        this.SubmitDate = SubmitDate;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean State) {
        this.State = State;
    }

    public boolean isIsReply() {
        return IsReply;
    }

    public void setIsReply(boolean IsReply) {
        this.IsReply = IsReply;
    }
}
