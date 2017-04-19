package com.goodo.pdjfy.email.model;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description 携带接收人拼接之后的字符串
 */

public class UsersBean {
    String mIds;
    String mNames;

    public UsersBean() {
        mIds = "";
        mNames = "";
    }

    public String getIds() {
        return mIds;
    }

    public void setIds(String ids) {
        mIds = ids;
    }

    public String getNames() {
        return mNames;
    }

    public void setNames(String names) {
        mNames = names;
    }
}
