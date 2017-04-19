package com.goodo.pdjfy.email.presenter;

import android.content.Intent;

import com.goodo.pdjfy.email.model.SendOuterEmailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public interface SendOuterPresenter {
    void sendOuterEmail(SendOuterEmailBean sendOuterEmailBean);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void selFile();
    void getFileBase64data(List<String> fileList, SendOuterEmailBean bean);
    void removeAttach(String path);
}
