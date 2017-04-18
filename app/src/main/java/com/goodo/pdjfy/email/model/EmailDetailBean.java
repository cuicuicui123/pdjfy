package com.goodo.pdjfy.email.model;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailDetailBean {
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
    private String Body;
    private String Date;
    private int Case_ID;
    private String CaseName;
    private int Mail_ID;

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
}
