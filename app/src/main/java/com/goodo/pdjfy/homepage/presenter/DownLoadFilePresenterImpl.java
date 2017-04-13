package com.goodo.pdjfy.homepage.presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.AttachBean;
import com.goodo.pdjfy.homepage.view.AttachView;
import com.goodo.pdjfy.util.FileDownLoadUtil;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class DownLoadFilePresenterImpl implements DownLoadFilePresenter {
    private AttachView mAttachView;
    private AppContext mAppContext;
    private BaseActivity mActivity;
    private AttachBean mAttachBean;
    private ProgressBar mProgressBar;
    private View mView;

    public DownLoadFilePresenterImpl(AttachView attachView, BaseActivity activity) {
        mAttachView = attachView;
        mAppContext = AppContext.getInstance();
        mActivity = activity;
    }

    @Override
    public void downLoadFile(AttachBean attachBean, final ProgressBar progressBar, View view) {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                mAttachBean = attachBean;
                mProgressBar = progressBar;
                mView = view;
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MyConfig.READ_STORAGE_CODE);
        } else {
            startDownLoad(attachBean, progressBar, view);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MyConfig.READ_STORAGE_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownLoad(mAttachBean, mProgressBar, mView);
            } else {
                Toast.makeText(mActivity, "需要读取文件权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startDownLoad(AttachBean attachBean, final ProgressBar progressBar, final View view) {
        FileDownLoadUtil fileDownLoadUtil = new FileDownLoadUtil(attachBean.getUrl(),
                MyConfig.getFileName(attachBean.getUrl()), mActivity,
                new FileDownLoadUtil.OnDownLoadSuccessListener() {
                    @Override
                    public void downLoadSuccess(File file) {
                        progressBar.setVisibility(View.INVISIBLE);
                        view.setEnabled(true);
                        Intent it = IntentUtil.getFileIntent(file);
                        mActivity.startActivity(it);
                    }
                },
                new FileDownLoadUtil.OnDownLoadFailListener() {
                    @Override
                    public void downLoadFail() {
                        progressBar.setVisibility(View.INVISIBLE);
                        view.setEnabled(true);
                        Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT).show();
                    }
        });
        fileDownLoadUtil.downLoad();
    }
}
