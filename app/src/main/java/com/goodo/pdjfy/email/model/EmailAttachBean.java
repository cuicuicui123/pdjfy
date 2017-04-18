package com.goodo.pdjfy.email.model;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailAttachBean {


    /**
     * ID : 6044
     * Url : 20170410125928_185_16907.xlsx
     * Name : 建设学校清单-下发版20170410.xlsx
     * Size : 63408
     */

    private int ID;
    private String Url;
    private String Name;
    private String Size;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }
}
