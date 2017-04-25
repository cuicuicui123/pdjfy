package com.goodo.app.email.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailDetailBean implements Parcelable {
    /**
     * Subject : 修改信息
     * From : 赵士果
     * To : 陈燕
     * Body : <p>区域教育中心：<br><br>沈兰&nbsp; &nbsp;中国工商银行 改为中国工商银行上海市剑河路支行</p><p>刘常庆&nbsp;&nbsp;&nbsp;中国农业银行 改为中国工商银行愚园路支行<br></p>
     * Date : 2017/4/18 13:21:38
     * Case_ID : 0
     * CaseName : 无
     * Mail_ID : 5746
     */

    private String Subject;
    private String From;
    private String To;
    private String Cc;
    private String Bcc;
    private String Body;
    private String Date;
    private int Case_ID;
    private String CaseName;
    private int Mail_ID;
    private String ToIDs;
    private String CcIDs;
    private String BccIDs;
    int OuterMailAddr_ID;
    int OuterMail_ID;
    int SendUser_ID;
    String SendUserName;
    String Receive_ID;
    int OuterReceive_ID;


    protected EmailDetailBean(Parcel in) {
        Subject = in.readString();
        From = in.readString();
        To = in.readString();
        Cc = in.readString();
        Bcc = in.readString();
        Body = in.readString();
        Date = in.readString();
        Case_ID = in.readInt();
        CaseName = in.readString();
        Mail_ID = in.readInt();
        ToIDs = in.readString();
        CcIDs = in.readString();
        BccIDs = in.readString();
        OuterMailAddr_ID = in.readInt();
        OuterMail_ID = in.readInt();
        SendUser_ID = in.readInt();
        SendUserName = in.readString();
        Receive_ID = in.readString();
        OuterReceive_ID = in.readInt();
    }

    public static final Creator<EmailDetailBean> CREATOR = new Creator<EmailDetailBean>() {
        @Override
        public EmailDetailBean createFromParcel(Parcel in) {
            return new EmailDetailBean(in);
        }

        @Override
        public EmailDetailBean[] newArray(int size) {
            return new EmailDetailBean[size];
        }
    };

    public int getOuterReceive_ID() {
        return OuterReceive_ID;
    }

    public void setOuterReceive_ID(int outerReceive_ID) {
        OuterReceive_ID = outerReceive_ID;
    }

    public String getReceive_ID() {
        return Receive_ID;
    }

    public void setReceive_ID(String receive_ID) {
        Receive_ID = receive_ID;
    }

    public int getSendUser_ID() {
        return SendUser_ID;
    }

    public void setSendUser_ID(int sendUser_ID) {
        SendUser_ID = sendUser_ID;
    }

    public String getSendUserName() {
        return SendUserName;
    }

    public void setSendUserName(String sendUserName) {
        SendUserName = sendUserName;
    }

    public int getOuterMailAddr_ID() {
        return OuterMailAddr_ID;
    }

    public void setOuterMailAddr_ID(int outerMailAddr_ID) {
        OuterMailAddr_ID = outerMailAddr_ID;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String From) {
        this.From = From;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String To) {
        this.To = To;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
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

    public int getMail_ID() {
        return Mail_ID;
    }

    public void setMail_ID(int Mail_ID) {
        this.Mail_ID = Mail_ID;
    }

    public String getToIDs() {
        return ToIDs;
    }

    public void setToIDs(String toIDs) {
        ToIDs = toIDs;
    }

    public String getCcIDs() {
        return CcIDs;
    }

    public void setCcIDs(String ccIDs) {
        CcIDs = ccIDs;
    }

    public String getBccIDs() {
        return BccIDs;
    }

    public void setBccIDs(String bccIDs) {
        BccIDs = bccIDs;
    }

    public String getCc() {
        return Cc;
    }

    public void setCc(String cc) {
        Cc = cc;
    }

    public String getBcc() {
        return Bcc;
    }

    public void setBcc(String bcc) {
        Bcc = bcc;
    }

    public int getOuterMail_ID() {
        return OuterMail_ID;
    }

    public void setOuterMail_ID(int outerMail_ID) {
        OuterMail_ID = outerMail_ID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Subject);
        dest.writeString(From);
        dest.writeString(To);
        dest.writeString(Cc);
        dest.writeString(Bcc);
        dest.writeString(Body);
        dest.writeString(Date);
        dest.writeInt(Case_ID);
        dest.writeString(CaseName);
        dest.writeInt(Mail_ID);
        dest.writeString(ToIDs);
        dest.writeString(CcIDs);
        dest.writeString(BccIDs);
        dest.writeInt(OuterMailAddr_ID);
        dest.writeInt(OuterMail_ID);
        dest.writeInt(SendUser_ID);
        dest.writeString(SendUserName);
        dest.writeString(Receive_ID);
        dest.writeInt(OuterReceive_ID);
    }
}
