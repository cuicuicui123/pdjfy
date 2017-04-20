package com.goodo.pdjfy.email.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.model.SendInnerEmailBean;
import com.goodo.pdjfy.email.view.SendInnerEmailView;
import com.goodo.pdjfy.rxjava.MySubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class TransmitInnerEmailPresenterImpl extends BaseSendInnerEmailPresenter {
    private int mIsInBox;
    private List<EmailAttachBean> mAttachBeanList;

    public TransmitInnerEmailPresenterImpl(SendInnerEmailView sendInnerEmailView, BaseActivity activity, int isInBox) {
        super(sendInnerEmailView, activity);
        mIsInBox = isInBox;
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
                        Toast.makeText(mActivity, "转发失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "转发失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mHttpMethods.transmitInnerEmail(bean, mIsInBox, subscriber);
    }

    @Override
    public void init(EmailDetailBean emailDetailBean, List<EmailAttachBean> list) {
        mAttachBeanList = list;
    }
}
