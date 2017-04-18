package com.goodo.pdjfy.email.view;

import com.goodo.pdjfy.email.model.EmailListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public interface EmailListView {
    void getEmailListBeanList(List<EmailListBean> list, boolean hasNewInfo, int dbSize);
}
