package com.goodo.app.schedule.model;

import java.io.Serializable;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class ScheduleBean implements Serializable {

    /**
     * ID : 228
     * Date : 2017/4/10 0:00:00
     * IsAllDay : 0
     * BeginTime : 09:00
     * EndTime : 11:00
     * Work : 部门业务学习
     * Address : 各部门自定
     * Type : 1
     */

    private int ID;
    private String Date;
    private int IsAllDay;
    private String BeginTime;
    private String EndTime;
    private String Work;
    private String Address;
    private int Type;
    private int mRow;
    private int mColumn;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public int getRow() {
        return mRow;
    }

    public void setRow(int row) {
        mRow = row;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int column) {
        mColumn = column;
    }

}
