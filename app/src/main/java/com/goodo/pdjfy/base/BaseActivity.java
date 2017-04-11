package com.goodo.pdjfy.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 所有activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initActivityConfig();
    }

    /**
     * 初始化activity视图
     */
    protected abstract void initContentView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化相关事件
     */
    protected abstract void initEvent();

    protected void initActivityConfig(){
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
