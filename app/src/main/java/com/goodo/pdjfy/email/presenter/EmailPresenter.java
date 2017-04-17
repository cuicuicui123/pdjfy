package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.email.model.OuterMailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public interface EmailPresenter {
    void getOuterMail();
    void getInnerReceiveClassify(List<OuterMailBean> list);
}
