package com.goodo.app.email.model;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class InnerMailBean {

    /**
     * ReceiveClassify_ID : 0
     * ReceiveClassifyName : 默认分类
     * Num : 2
     */

    private int ReceiveClassify_ID;
    private String ReceiveClassifyName;
    private int Num;

    public int getReceiveClassify_ID() {
        return ReceiveClassify_ID;
    }

    public void setReceiveClassify_ID(int ReceiveClassify_ID) {
        this.ReceiveClassify_ID = ReceiveClassify_ID;
    }

    public String getReceiveClassifyName() {
        return ReceiveClassifyName;
    }

    public void setReceiveClassifyName(String ReceiveClassifyName) {
        this.ReceiveClassifyName = ReceiveClassifyName;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int Num) {
        this.Num = Num;
    }
}
