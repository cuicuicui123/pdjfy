package com.goodo.app.homepage.model;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class HomePageDetailBean {
    /**
     * ContentTitle : 花开有时，“未来”正来——浦东、宝山、崇明三区联合举办语文教学主题研讨活动
     * UserName : 秦红斌
     * Content : <p>
     */

    private String ContentTitle;
    private String UserName;
    private String Content;
    private String SubmitDate;
    private int ICount;

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

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String submitDate) {
        SubmitDate = submitDate;
    }

    public int getICount() {
        return ICount;
    }

    public void setICount(int ICount) {
        this.ICount = ICount;
    }
}
