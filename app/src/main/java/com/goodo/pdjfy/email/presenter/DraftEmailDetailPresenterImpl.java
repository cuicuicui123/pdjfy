package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.model.SendInnerEmailBean;
import com.goodo.pdjfy.email.view.SendInnerEmailView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class DraftEmailDetailPresenterImpl extends BaseSendInnerEmailPresenter {
    private List<EmailAttachBean> mEmailAttachBeanList;

    public DraftEmailDetailPresenterImpl(SendInnerEmailView sendInnerEmailView, BaseActivity activity) {
        super(sendInnerEmailView, activity);
    }

    @Override
    public void init(EmailDetailBean emailDetailBean, List<EmailAttachBean> list) {
        mReceiverUsersBean.setIds(emailDetailBean.getToIDs());
        mReceiverUsersBean.setNames(emailDetailBean.getTo());
        mCcUsersBean.setIds(emailDetailBean.getCcIDs());
        mCcUsersBean.setNames(emailDetailBean.getCc());
        mBccUsersBean.setIds(emailDetailBean.getBccIDs());
        mBccUsersBean.setNames(emailDetailBean.getBcc());
        mEmailView.getSelReceiverPerson(mReceiverUsersBean);
        mEmailView.getSelCcPerson(mCcUsersBean);
        mEmailView.getSelBccPerson(mBccUsersBean);
        mEmailAttachBeanList = list;
    }
}
