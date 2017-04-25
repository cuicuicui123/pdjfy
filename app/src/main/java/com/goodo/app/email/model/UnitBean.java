package com.goodo.app.email.model;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class UnitBean {

    /**
     * ID : 7
     * NAME : 教师发展中心
     * T : 1
     */

    private int ID;
    private String NAME;
    private int T;
    private boolean mIsSelected;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getT() {
        return T;
    }

    public void setT(int T) {
        this.T = T;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
