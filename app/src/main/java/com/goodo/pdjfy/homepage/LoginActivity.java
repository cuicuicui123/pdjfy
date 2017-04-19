package com.goodo.pdjfy.homepage;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.presenter.LoginPresenter;
import com.goodo.pdjfy.homepage.presenter.LoginPresenterImpl;
import com.goodo.pdjfy.homepage.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 登录页面
 */

public class LoginActivity extends BaseActivity implements LoginView {
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.edt_account)
    EditText mAccountEdt;
    @BindView(R.id.edt_pwd)
    EditText mPwdEdt;
    @BindView(R.id.ll_remember_pwd)
    LinearLayout mRememberPwdLl;
    @BindView(R.id.tv_remember)
    TextView mRememberTv;
    @BindView(R.id.btn_login)
    Button mLoginBtn;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mLoginPresenter = new LoginPresenterImpl(this, this);
        mLoginPresenter.getRememberFroSharedPreference();
    }

    @Override
    protected void initEvent() {
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRememberPwdLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLoginPresenter.getRemember()) {
                    mLoginPresenter.rememberPwd(false);
                    mRememberTv.setText("");
                } else {
                    mLoginPresenter.rememberPwd(true);
                    mRememberTv.setText("√");
                }
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginBtn.setEnabled(false);
                mLoginPresenter.login(mAccountEdt.getText().toString(),
                        mPwdEdt.getText().toString());
            }
        });

    }

    @Override
    public void getRememberAccountAndPwd(String account, String pwd) {
        mAccountEdt.setText(account);
        mPwdEdt.setText(pwd);
        mRememberTv.setText("√");
    }

    @Override
    public void isLoginSucceed(boolean succeed) {
        mLoginBtn.setEnabled(true);
        if (!succeed) {
            Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
        }
    }
}
