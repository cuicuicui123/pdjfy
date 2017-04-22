package com.goodo.pdjfy.email.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.model.SendOuterEmailBean;
import com.goodo.pdjfy.email.view.SendOuterEmailView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.rxjava.MySubscriber;
import com.goodo.pdjfy.util.GetPathFromUri4kitkat;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public abstract class BaseSendOuterEmailPresenter implements SendOuterEmailPresenter {
    protected HttpMethods mHttpMethods;
    protected BaseActivity mActivity;
    protected SendOuterEmailView mSendOuterEmailView;
    protected List<String> mAttachList;

    public BaseSendOuterEmailPresenter(BaseActivity activity, SendOuterEmailView sendOuterEmailView) {
        mActivity = activity;
        mSendOuterEmailView = sendOuterEmailView;
        mHttpMethods = HttpMethods.getInstance();
        mAttachList = new ArrayList<>();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case MyConfig.KEY_SEL_FILE:
                    String path = GetPathFromUri4kitkat.getPath(mActivity, data.getData());
                    mAttachList.add(path);
                    mSendOuterEmailView.getSelAttach(path);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void sendOuterEmail(SendOuterEmailBean sendOuterEmailBean) {
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
                        Toast.makeText(mActivity, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mHttpMethods.sendOuterEmail(sendOuterEmailBean, subscriber);
    }

    @Override
    public void selFile() {
        Intent it = IntentUtil.getSelectFileIntent();
        mActivity.startActivityForResult(it, MyConfig.KEY_SEL_FILE);
    }

    /**
     * 发送时候将附件解析成base64字符串
     * @param fileList 附件列表
     */
    @Override
    public void getFileBase64data(List<String> fileList, SendOuterEmailBean bean) {
        String mFileName = "";
        String mBase64Data = "";
        for(int i = 0;i < fileList.size();i ++){
            String file = fileList.get(i);
            String[] files = file.split("/");
            int length = files.length;
            String fileName = files[length - 1];
            String base64str = null;
            try {
                File f = new File(file);
                FileInputStream fin = new FileInputStream(f);
                byte[] buff = new byte[(int) f.length()];
                fin.read(buff);
                fin.close();
                byte[] encode = Base64.encode(buff, Base64.DEFAULT);
                base64str = new String(encode, "utf-8");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(i == 0){
                mFileName = fileName;
                mBase64Data = base64str;
            }else{
                mFileName = mFileName + "," + fileName;
                mBase64Data = mBase64Data + "," + base64str;
            }
        }
        bean.setFileNames(mFileName);
        bean.setBase64Data(mBase64Data);
    }

    @Override
    public void removeAttach(String path) {
        mAttachList.remove(path);
    }

    @Override
    public List<String> getAttachList() {
        return mAttachList;
    }

    @Override
    public void init(EmailDetailBean bean, List<EmailAttachBean> list) {

    }
}
