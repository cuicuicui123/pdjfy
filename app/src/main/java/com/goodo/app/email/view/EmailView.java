package com.goodo.app.email.view;

import com.goodo.app.email.model.EmailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public interface EmailView {
    void getMailBeanList(List<EmailBean> list);
}
