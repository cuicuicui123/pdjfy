package com.goodo.app.email.presenter;

import android.content.Intent;

import com.goodo.app.email.model.EmailAttachBean;
import com.goodo.app.email.model.EmailDetailBean;
import com.goodo.app.email.model.SendInnerEmailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public interface SendInnerEmailPresenter {
    void selPerson(int code);
    void selFile();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void removeAttach(String path);
    List<String> getAttachList();
    void getReceivers(SendInnerEmailBean bean);
    void sendInnerEmail(SendInnerEmailBean bean);
    void getFileBase64data(List<String> fileList, SendInnerEmailBean bean);
    void init(EmailDetailBean emailDetailBean, List<EmailAttachBean> list);
}
