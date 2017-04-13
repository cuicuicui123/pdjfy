package com.goodo.pdjfy.announcement.model;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class AnnounceDetailBean {

    /**
     * ContentTitle : 4月17日全院大会
     * UserName : 宋书菊
     * SubmitDate : 2017-04-11 15:23:00
     * ICount : 49
     * Content :
     */

    private String ContentTitle;
    private String UserName;
    private String SubmitDate;
    private int ICount;
    private String Content;

    public String getContentTitle() {
        return ContentTitle;
    }

    public void setContentTitle(String ContentTitle) {
        this.ContentTitle = ContentTitle;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String SubmitDate) {
        this.SubmitDate = SubmitDate;
    }

    public int getICount() {
        return ICount;
    }

    public void setICount(int ICount) {
        this.ICount = ICount;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
}
