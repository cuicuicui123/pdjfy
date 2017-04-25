package com.goodo.app.email.presenter;

import com.goodo.app.email.model.OuterMailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public interface EmailPresenter {
    void getOuterMail();
    void getInnerReceiveClassify(List<OuterMailBean> list);
    void startToSendEmailActivity();
}
