package com.goodo.app.email.presenter;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.EmailAttachBean;
import com.goodo.app.email.model.EmailDetailBean;
import com.goodo.app.email.model.SendInnerEmailBean;
import com.goodo.app.email.view.SendInnerEmailView;

import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class TrashEmailDetailPresenterImpl extends BaseSendInnerEmailPresenter {
    private List<EmailAttachBean> mEmailAttachBeanList;

    public TrashEmailDetailPresenterImpl(SendInnerEmailView sendInnerEmailView, BaseActivity activity) {
        super(sendInnerEmailView, activity);
    }

    @Override
    public void sendInnerEmail(SendInnerEmailBean bean) {
        String OriginAttachs = "";
        int size = mEmailAttachBeanList.size();
        for(int i = 0;i < size;i ++){
            EmailAttachBean attachBean = mEmailAttachBeanList.get(i);
            if(i == 0){
                OriginAttachs = "0|" + attachBean.getName() + "|0|" + attachBean.getUrl();
            }else{
                OriginAttachs = OriginAttachs + ",0|" + attachBean.getName() + "|0|" + attachBean.getUrl();
            }
        }
        bean.setOriginAttachs(OriginAttachs);
        super.sendInnerEmail(bean);
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
