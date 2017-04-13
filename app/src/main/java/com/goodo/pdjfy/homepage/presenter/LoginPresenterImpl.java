package com.goodo.pdjfy.homepage.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.LoginBean;
import com.goodo.pdjfy.homepage.model.UserBean;
import com.goodo.pdjfy.homepage.view.LoginView;
import com.goodo.pdjfy.main.MainActivity;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.rxjava.MySubscriber;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 登录逻辑接口实现类
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView mLoginView;
    private BaseActivity mActivity;

    private boolean mRemember;

    private String KEY_SHARED_PREFERENCE_LOGIN = "login";
    private String KEY_SHARED_PREFERENCE_REMEMBER = "remember";
    private String KEY_SHARED_PREFERENCE_ACCOUNT = "account";
    private String KEY_SHARED_PREFERENCE_PWD = "pwd";

    private String mOnLineType = "64";
    private String mDeviceName = "2";
    private String mPlace = "1";

    private static UserBean mUserBean;

    private HttpMethods mHttpMethods;

    public LoginPresenterImpl(LoginView loginView, BaseActivity activity) {
        mLoginView = loginView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getRememberFroSharedPreference() {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(KEY_SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
        mRemember = sharedPreferences.getBoolean(KEY_SHARED_PREFERENCE_REMEMBER, false);
        if (mRemember) {
            String account = sharedPreferences.getString(KEY_SHARED_PREFERENCE_ACCOUNT, "");
            String pwd = sharedPreferences.getString(KEY_SHARED_PREFERENCE_PWD, "");
            mLoginView.getRememberAccountAndPwd(account, pwd);
        }
    }

    @Override
    public void saveRememberToSharedPreference(String account, String pwd) {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences(KEY_SHARED_PREFERENCE_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SHARED_PREFERENCE_REMEMBER, mRemember);
        if (mRemember) {
            editor.putString(KEY_SHARED_PREFERENCE_ACCOUNT, account);
            editor.putString(KEY_SHARED_PREFERENCE_PWD, pwd);
        }
        editor.commit();
    }

    @Override
    public void rememberPwd(boolean remember) {
        mRemember = remember;
    }

    @Override
    public void login(String account, String pwd) {
        saveRememberToSharedPreference(account, pwd);
        LoginBean loginBean = new LoginBean();
        loginBean.setAccount(account);
        loginBean.setPwd(pwd);
        loginBean.setOnLineType(mOnLineType);
        loginBean.setDeviceName(mDeviceName);
        loginBean.setPlace(mPlace);
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    Gson gson = new Gson();
                    mUserBean = gson.fromJson(Goodo.toString(), UserBean.class);
                    if (mUserBean.getEID() == 0) {//EID为0代表登录成功
                        startToMainActivity();
                        mLoginView.isLoginSucceed(true);
                    } else {
                        mLoginView.isLoginSucceed(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mLoginView.isLoginSucceed(false);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mLoginView.isLoginSucceed(false);
            }
        };
        mHttpMethods.login(loginBean, subscriber);
    }

    @Override
    public boolean getRemember() {
        return mRemember;
    }

    @Override
    public void startToMainActivity() {
        Intent it = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(it);
        mActivity.finish();
    }

    public static UserBean getUserBean(){
        return mUserBean;
    }

}
