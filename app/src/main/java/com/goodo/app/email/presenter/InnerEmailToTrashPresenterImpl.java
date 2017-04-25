package com.goodo.app.email.presenter;

import android.app.Activity;
import android.util.Base64;
import android.widget.Toast;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.model.SendInnerEmailBean;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.rxjava.MySubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class InnerEmailToTrashPresenterImpl implements InnerEmailToTrashPresenter {
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;

    public InnerEmailToTrashPresenterImpl(BaseActivity activity) {
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void toTrash(List<String> attachList, SendInnerEmailBean bean) {
        getFileBase64data(attachList, bean);
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    if (Goodo.getInt("EID") == 0) {
                        mActivity.setResult(Activity.RESULT_OK);
                        mActivity.finish();
                    } else {
                        Toast.makeText(mActivity, "存草稿失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "存草稿失败！", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mHttpMethods.innerEmailToTrash(bean, subscriber);
    }

    /**
     * 发送时候将附件解析成base64字符串
     * @param fileList 附件列表
     */
    public void getFileBase64data(List<String> fileList, SendInnerEmailBean bean) {
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
}
