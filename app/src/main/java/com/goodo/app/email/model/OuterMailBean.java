package com.goodo.app.email.model;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class OuterMailBean {

    /**
     * OuterMailAddr_ID : 4
     * OuterMailAddr : pdwangzhanzu@163.com
     * OuterMailName : pdwangzhanzu
     * Num : 71
     */

    private int OuterMailAddr_ID;
    private String OuterMailAddr;
    private String OuterMailName;
    private int Num;

    public int getOuterMailAddr_ID() {
        return OuterMailAddr_ID;
    }

    public void setOuterMailAddr_ID(int OuterMailAddr_ID) {
        this.OuterMailAddr_ID = OuterMailAddr_ID;
    }

    public String getOuterMailAddr() {
        return OuterMailAddr;
    }

    public void setOuterMailAddr(String OuterMailAddr) {
        this.OuterMailAddr = OuterMailAddr;
    }

    public String getOuterMailName() {
        return OuterMailName;
    }

    public void setOuterMailName(String OuterMailName) {
        this.OuterMailName = OuterMailName;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int Num) {
        this.Num = Num;
    }
}
