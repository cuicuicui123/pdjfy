package com.goodo.pdjfy.homepage.view;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public interface LoginView {
    void getRememberAccountAndPwd(String account, String pwd);
    void isLoginSucceed(boolean succeed);
}
