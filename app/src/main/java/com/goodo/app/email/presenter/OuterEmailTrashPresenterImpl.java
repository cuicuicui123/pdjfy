package com.goodo.app.email.presenter;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.EmailAttachBean;
import com.goodo.app.email.model.EmailDetailBean;
import com.goodo.app.email.model.SendOuterEmailBean;
import com.goodo.app.email.view.SendOuterEmailView;

import java.util.List;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class OuterEmailTrashPresenterImpl extends BaseSendOuterEmailPresenter {
    private List<EmailAttachBean> mAttachBeanList;

    public OuterEmailTrashPresenterImpl(BaseActivity activity, SendOuterEmailView sendOuterEmailView) {
        super(activity, sendOuterEmailView);
    }

    @Override
    public void sendOuterEmail(SendOuterEmailBean sendOuterEmailBean) {
        String OriginAttachs = "";
        int size = mAttachBeanList.size();
        for(int i = 0;i < size;i ++){
            EmailAttachBean attachBean = mAttachBeanList.get(i);
            if(i == 0){
                OriginAttachs = "0|" + attachBean.getName() + "|0|" + attachBean.getUrl();
            }else{
                OriginAttachs = OriginAttachs + ",0|" + attachBean.getName() + "|0|" + attachBean.getUrl();
            }
        }
        sendOuterEmailBean.setOriginAttachs(OriginAttachs);
        super.sendOuterEmail(sendOuterEmailBean);
    }

    @Override
    public void init(EmailDetailBean bean, List<EmailAttachBean> list) {
        mAttachBeanList = list;
    }
}
