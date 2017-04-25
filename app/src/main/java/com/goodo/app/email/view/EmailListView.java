package com.goodo.app.email.view;

import com.goodo.app.email.model.EmailListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public interface EmailListView {
    void getEmailListBeanList(List<EmailListBean> list, boolean hasNewInfo, int dbSize);
}
