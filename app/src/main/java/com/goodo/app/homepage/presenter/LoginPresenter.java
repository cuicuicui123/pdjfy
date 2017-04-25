package com.goodo.app.homepage.presenter;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 登录逻辑接口
 */

public interface LoginPresenter {
    void getRememberFroSharedPreference();
    void saveRememberToSharedPreference(String account, String pwd);
    void rememberPwd(boolean remember);
    void login(String account, String pwd);
    boolean getRemember();
    void startToMainActivity();
}
