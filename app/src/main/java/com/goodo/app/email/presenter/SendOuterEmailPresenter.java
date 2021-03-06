package com.goodo.app.email.presenter;

import android.content.Intent;

import com.goodo.app.email.model.EmailAttachBean;
import com.goodo.app.email.model.EmailDetailBean;
import com.goodo.app.email.model.SendOuterEmailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public interface SendOuterEmailPresenter {
    void sendOuterEmail(SendOuterEmailBean sendOuterEmailBean);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void selFile();
    void getFileBase64data(List<String> fileList, SendOuterEmailBean bean);
    void removeAttach(String path);
    List<String> getAttachList();
    void init(EmailDetailBean bean, List<EmailAttachBean> list);
}
