package com.goodo.app.email.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.EmailAttachBean;
import com.goodo.app.email.model.EmailDetailBean;
import com.goodo.app.email.model.SendOuterEmailBean;
import com.goodo.app.email.view.SendOuterEmailView;
import com.goodo.app.rxjava.MySubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class ReplyOuterEmailPresenterImpl extends BaseSendOuterEmailPresenter {
    private List<EmailAttachBean> mAttachBeanList;

    public ReplyOuterEmailPresenterImpl(BaseActivity activity, SendOuterEmailView sendOuterEmailView) {
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
        getFileBase64data(mAttachList, sendOuterEmailBean);
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
        mHttpMethods.replyOuterEmail(sendOuterEmailBean, subscriber);
    }

    @Override
    public void init(EmailDetailBean bean, List<EmailAttachBean> list) {
        mAttachBeanList = list;
    }
}
