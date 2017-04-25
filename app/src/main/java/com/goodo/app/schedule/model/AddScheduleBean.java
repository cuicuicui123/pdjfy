package com.goodo.app.schedule.model;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class AddScheduleBean {
    String mDate;
    int mIsAllDay;
    String beginTime;
    String endTime;
    String mWork;
    String mContent;
    String mAddress;
    String relatedUser;
    int mCaseId;
    String mCaseName;
    int id;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getIsAllDay() {
        return mIsAllDay;
    }

    public void setAllDay(int allDay) {
        mIsAllDay = allDay;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWork() {
        return mWork;
    }

    public void setWork(String work) {
        mWork = work;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getRelatedUser() {
        return relatedUser;
    }

    public void setRelatedUser(String relatedUser) {
        this.relatedUser = relatedUser;
    }

    public int getCaseId() {
        return mCaseId;
    }

    public void setCaseId(int caseId) {
        mCaseId = caseId;
    }

    public String getCaseName() {
        return mCaseName;
    }

    public void setCaseName(String caseName) {
        mCaseName = caseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
