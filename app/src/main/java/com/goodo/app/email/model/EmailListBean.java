package com.goodo.app.email.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description 收件箱Receive_ID发件箱Mail_ID
 */

public class EmailListBean implements Parcelable {

    /**
     * rowNum : 1
     * Receive_ID : 19964
     * Subject : 建设学校清单-下发版20170410.xlsx
     * Date : 2017/4/10 12:59:35
     * MailStatus : 1
     * From : 周伟
     * ReceiveUser_ID : 158
     * ReceiveClassify_ID : 0
     * Case_ID : 0
     * CaseName : 无
     * Item_ID : 10102
     * IsTop : 0
     * IsRead : 1
     * IsAttached : 1
     * OuterMailAddr_ID : 0
     * "To": "沈军妹"
     * "Mail_ID": "5667"
     */

    private int rowNum;
    private int Receive_ID;
    private String Subject;
    private String Date;
    private int MailStatus;
    private String From;
    private int ReceiveUser_ID;
    private int ReceiveClassify_ID;
    private int Case_ID;
    private String CaseName;
    private int Item_ID;
    private boolean IsTop;
    private int IsRead;
    private boolean IsAttached;
    private int OuterMailAddr_ID;
    private String To;
    private int Mail_ID;

    protected EmailListBean(Parcel in) {
        rowNum = in.readInt();
        Receive_ID = in.readInt();
        Subject = in.readString();
        Date = in.readString();
        MailStatus = in.readInt();
        From = in.readString();
        ReceiveUser_ID = in.readInt();
        ReceiveClassify_ID = in.readInt();
        Case_ID = in.readInt();
        CaseName = in.readString();
        Item_ID = in.readInt();
        IsTop = in.readByte() != 0;
        IsRead = in.readByte();
        IsAttached = in.readByte() != 0;
        OuterMailAddr_ID = in.readInt();
        To = in.readString();
        Mail_ID = in.readInt();
    }

    public static final Creator<EmailListBean> CREATOR = new Creator<EmailListBean>() {
        @Override
        public EmailListBean createFromParcel(Parcel in) {
            return new EmailListBean(in);
        }

        @Override
        public EmailListBean[] newArray(int size) {
            return new EmailListBean[size];
        }
    };

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getReceive_ID() {
        return Receive_ID;
    }

    public void setReceive_ID(int Receive_ID) {
        this.Receive_ID = Receive_ID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public int getMailStatus() {
        return MailStatus;
    }

    public void setMailStatus(int MailStatus) {
        this.MailStatus = MailStatus;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public int getReceiveUser_ID() {
        return ReceiveUser_ID;
    }

    public void setReceiveUser_ID(int ReceiveUser_ID) {
        this.ReceiveUser_ID = ReceiveUser_ID;
    }

    public int getReceiveClassify_ID() {
        return ReceiveClassify_ID;
    }

    public void setReceiveClassify_ID(int ReceiveClassify_ID) {
        this.ReceiveClassify_ID = ReceiveClassify_ID;
    }

    public int getCase_ID() {
        return Case_ID;
    }

    public void setCase_ID(int Case_ID) {
        this.Case_ID = Case_ID;
    }

    public String getCaseName() {
        return CaseName;
    }

    public void setCaseName(String CaseName) {
        this.CaseName = CaseName;
    }

    public int getItem_ID() {
        return Item_ID;
    }

    public void setItem_ID(int Item_ID) {
        this.Item_ID = Item_ID;
    }

    public boolean isIsTop() {
        return IsTop;
    }

    public void setIsTop(boolean IsTop) {
        this.IsTop = IsTop;
    }

    public int isIsRead() {
        return IsRead;
    }

    public void setIsRead(int IsRead) {
        this.IsRead = IsRead;
    }

    public boolean isIsAttached() {
        return IsAttached;
    }

    public void setIsAttached(boolean IsAttached) {
        this.IsAttached = IsAttached;
    }

    public int getOuterMailAddr_ID() {
        return OuterMailAddr_ID;
    }

    public void setOuterMailAddr_ID(int OuterMailAddr_ID) {
        this.OuterMailAddr_ID = OuterMailAddr_ID;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public int getMail_ID() {
        return Mail_ID;
    }

    public void setMail_ID(int mail_ID) {
        Mail_ID = mail_ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rowNum);
        dest.writeInt(Receive_ID);
        dest.writeString(Subject);
        dest.writeString(Date);
        dest.writeInt(MailStatus);
        dest.writeString(From);
        dest.writeInt(ReceiveUser_ID);
        dest.writeInt(ReceiveClassify_ID);
        dest.writeInt(Case_ID);
        dest.writeString(CaseName);
        dest.writeInt(Item_ID);
        dest.writeByte((byte) (IsTop ? 1 : 0));
        dest.writeInt(IsRead);
        dest.writeByte((byte) (IsAttached ? 1 : 0));
        dest.writeInt(OuterMailAddr_ID);
        dest.writeString(To);
        dest.writeInt(Mail_ID);
    }
}
