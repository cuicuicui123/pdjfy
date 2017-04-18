package com.goodo.pdjfy.schedule.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class ScheduleDetailBean implements Parcelable {

    /**
     * Date : 2017/4/11 0:00:00
     * IsAllDay : 0
     * BeginTime : 09:30
     * EndTime : 16:00
     * Work : 城乡一体化项目招标联系招标中心
     * Address :
     */
    private int mId;
    private String Date;
    private int IsAllDay;
    private String BeginTime;
    private String EndTime;
    private String Work;
    private String Address;
    private String Content;

    protected ScheduleDetailBean(Parcel in) {
        mId = in.readInt();
        Date = in.readString();
        IsAllDay = in.readInt();
        BeginTime = in.readString();
        EndTime = in.readString();
        Work = in.readString();
        Address = in.readString();
        Content = in.readString();
    }

    public static final Creator<ScheduleDetailBean> CREATOR = new Creator<ScheduleDetailBean>() {
        @Override
        public ScheduleDetailBean createFromParcel(Parcel in) {
            return new ScheduleDetailBean(in);
        }

        @Override
        public ScheduleDetailBean[] newArray(int size) {
            return new ScheduleDetailBean[size];
        }
    };

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

    public int getIsAllDay() {
        return IsAllDay;
    }

    public void setIsAllDay(int IsAllDay) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mId);
        dest.writeString(Date);
        dest.writeInt(IsAllDay);
        dest.writeString(BeginTime);
        dest.writeString(EndTime);
        dest.writeString(Work);
        dest.writeString(Address);
        dest.writeString(Content);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

}
