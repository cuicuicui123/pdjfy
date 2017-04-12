package com.goodo.pdjfy.main;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class MainActivity extends BaseActivity {
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
}
