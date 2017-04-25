package com.goodo.app.email.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.EmailAttachBean;
import com.goodo.app.email.model.EmailDetailBean;
import com.goodo.app.email.model.SendInnerEmailBean;
import com.goodo.app.email.view.SendInnerEmailView;
import com.goodo.app.rxjava.MySubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class ReplyInnerEmailPresenterImpl extends BaseSendInnerEmailPresenter {
    private List<EmailAttachBean> mAttachBeanList;

    public ReplyInnerEmailPresenterImpl(SendInnerEmailView sendInnerEmailView, BaseActivity activity) {
        super(sendInnerEmailView, activity);
    }

    @Override
    public void sendInnerEmail(SendInnerEmailBean bean) {
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
        bean.setOriginAttachs(OriginAttachs);
        bean.setToIds(mReceiverUsersBean.getIds());
        bean.setToName(mReceiverUsersBean.getNames());
        bean.setCcIds(mCcUsersBean.getIds());
        bean.setCcName(mCcUsersBean.getNames());
        bean.setBccIds(mBccUsersBean.getIds());
        bean.setBccName(mBccUsersBean.getNames());
        getFileBase64data(mAttachList, bean);
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    if (Goodo.getInt("EID") == 0) {
                        mActivity.setResult(Activity.RESULT_OK, mActivity.getIntent());
                        mActivity.finish();
                    } else {
                        Toast.makeText(mActivity, "回复失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "回复失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mHttpMethods.replyInnerEmail(bean, subscriber);
    }

    @Override
    public void init(EmailDetailBean emailDetailBean, List<EmailAttachBean> list) {
        mAttachBeanList = list;
        mReceiverUsersBean.setIds(emailDetailBean.getSendUser_ID() + "|0|" + emailDetailBean.getSendUserName() + "|默认分类");
        mReceiverUsersBean.setNames(emailDetailBean.getSendUserName());
        mEmailView.getSelReceiverPerson(mReceiverUsersBean);
    }
}
