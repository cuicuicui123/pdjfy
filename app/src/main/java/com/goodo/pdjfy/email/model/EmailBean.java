package com.goodo.pdjfy.email.model;

import java.util.List;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class EmailBean {
    String title;
    List<ChildEmailBean> mList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildEmailBean> getList() {
        return mList;
    }

    public void setList(List<ChildEmailBean> list) {
        mList = list;
    }

    public static class ChildEmailBean{
        String mContent;
        int mPicRes;

        public String getContent() {
            return mContent;
        }

        public void setContent(String content) {
            mContent = content;
        }

        public int getPicRes() {
            return mPicRes;
        }

        public void setPicRes(int picRes) {
            mPicRes = picRes;
        }
    }
}
