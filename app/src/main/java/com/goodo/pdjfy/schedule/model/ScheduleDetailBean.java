package com.goodo.pdjfy.schedule.model;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class ScheduleDetailBean {

    /**
     * Date : 2017/4/11 0:00:00
     * IsAllDay : 0
     * BeginTime : 09:30
     * EndTime : 16:00
     * Work : 城乡一体化项目招标联系招标中心
     * Address :
     */

    private String Date;
    private boolean IsAllDay;
    private String BeginTime;
    private String EndTime;
    private String Work;
    private String Address;
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public boolean isIsAllDay() {
        return IsAllDay;
    }

    public void setIsAllDay(boolean IsAllDay) {
        this.IsAllDay = IsAllDay;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String BeginTime) {
        this.BeginTime = BeginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getWork() {
        return Work;
    }

    public void setWork(String Work) {
        this.Work = Work;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
}
