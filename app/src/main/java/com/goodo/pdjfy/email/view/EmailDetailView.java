package com.goodo.pdjfy.email.view;

import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public interface EmailDetailView {
    void getEmailDetail(EmailDetailBean bean);
    void getEmailAttachList(List<EmailAttachBean> list);
}
