package com.goodo.pdjfy.email.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.SelectPersonActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.model.SendInnerEmailBean;
import com.goodo.pdjfy.email.model.UnitUserBean;
import com.goodo.pdjfy.email.model.UsersBean;
import com.goodo.pdjfy.email.view.SendInnerEmailView;
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

import rx.Subscriber;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public abstract class BaseSendInnerEmailPresenter implements SendInnerPresenter {
    protected SendInnerEmailView mEmailView;
    protected BaseActivity mActivity;
    protected HttpMethods mHttpMethods;
    protected UsersBean mReceiverUsersBean;
    protected UsersBean mCcUsersBean;
    protected UsersBean mBccUsersBean;
    protected List<String> mAttachList;

    public BaseSendInnerEmailPresenter(SendInnerEmailView sendInnerEmailView, BaseActivity activity) {
        mEmailView =  sendInnerEmailView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
        mAttachList = new ArrayList<>();
        mReceiverUsersBean = new UsersBean();
        mCcUsersBean = new UsersBean();
        mBccUsersBean = new UsersBean();
    }

    @Override
    public void selPerson(int code) {
        Intent it = new Intent(mActivity, SelectPersonActivity.class);
        mActivity.startActivityForResult(it, code);
    }

    @Override
    public void selFile() {
        Intent it = IntentUtil.getSelectFileIntent();
        mActivity.startActivityForResult(it, MyConfig.KEY_SEL_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case MyConfig.SEL_RECEIVER_CODE:
                    mReceiverUsersBean = getSelPersonName(data);
                    mEmailView.getSelReceiverPerson(mReceiverUsersBean);
                    break;
                case MyConfig.SEL_CC_CODE:
                    mCcUsersBean = getSelPersonName(data);
                    mEmailView.getSelCcPerson(mCcUsersBean);
                    break;
                case MyConfig.SEL_BCC_CODE:
                    mBccUsersBean = getSelPersonName(data);
                    mEmailView.getSelBccPerson(mBccUsersBean);
                    break;
                case MyConfig.KEY_SEL_FILE:
                    String path = GetPathFromUri4kitkat.getPath(mActivity, data.getData());
                    mAttachList.add(path);
                    mEmailView.getSelAttach(path);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void sendInnerEmail(SendInnerEmailBean bean) {
        if (mReceiverUsersBean.getIds().equals("")) {
            Toast.makeText(mActivity, "选择接收人", Toast.LENGTH_SHORT).show();
        } else {
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
            mHttpMethods.sendInnerEmail(bean, subscriber);
        }
    }

    @Override
    public void removeAttach(String path) {
        mAttachList.remove(path);
    }

    private UsersBean getSelPersonName(Intent data){
        String names = "";
        String ids = "";
        List<UnitUserBean> list = (List<UnitUserBean>) data.getSerializableExtra(MyConfig.KEY_SEND_LIST);
        for (int i = 0;i < list.size();i ++) {
            UnitUserBean bean = list.get(i);
            if (i == 0) {
                names = bean.getUserName();
                ids = bean.getUser_ID() + "|0|" + bean.getUserName() + "|默认分类";
            } else {
                names = names + "、" + bean.getUserName();
                ids = ids + ";" + bean.getUser_ID() + "|0|" + bean.getUserName()+"|默认分类";
            }
        }
        UsersBean usersBean = new UsersBean();
        usersBean.setNames(names);
        usersBean.setIds(ids);
        return usersBean;
    }

    /**
     * 发送时候将附件解析成base64字符串
     * @param fileList 附件列表
     */
    @Override
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

    @Override
    public void init(EmailDetailBean emailDetailBean, List<EmailAttachBean> list) {

    }

    @Override
    public List<String> getAttachList() {
        return mAttachList;
    }

    @Override
    public void getReceivers(SendInnerEmailBean bean) {
        bean.setToIds(mReceiverUsersBean.getIds());
        bean.setToName(mReceiverUsersBean.getNames());
        bean.setCcIds(mCcUsersBean.getIds());
        bean.setCcName(mCcUsersBean.getNames());
        bean.setBccIds(mBccUsersBean.getIds());
        bean.setBccName(mBccUsersBean.getNames());
    }
}
