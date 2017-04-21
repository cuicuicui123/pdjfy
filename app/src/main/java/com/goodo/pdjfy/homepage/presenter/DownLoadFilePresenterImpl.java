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
    private AppContext mAppContext;
    private BaseActivity mActivity;
    private String mUrl;
    private String mFileName;
    private ProgressBar mProgressBar;
    private View mView;
    private boolean mIsBase64;
    private boolean mIsXml;

    public DownLoadFilePresenterImpl(BaseActivity activity) {
        mAppContext = AppContext.getInstance();
        mActivity = activity;
    }

    @Override
    public void downLoadFile(String url, String fileName, final ProgressBar progressBar, View view, boolean isBase64, boolean isXml) {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
            mUrl = url;
            mFileName = fileName;
            mProgressBar = progressBar;
            mView = view;
            mIsBase64 = isBase64;
            mIsXml = isXml;
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MyConfig.READ_STORAGE_CODE);
        } else {
            startDownLoad(url, fileName, progressBar, view, isBase64, isXml);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MyConfig.READ_STORAGE_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownLoad(mUrl, mFileName, mProgressBar, mView, mIsBase64, mIsXml);
            } else {
                Toast.makeText(mActivity, "需要读取文件权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startDownLoad(String url, String fileName, final ProgressBar progressBar, final View view, boolean isBase64, boolean isXml) {
        FileDownLoadUtil fileDownLoadUtil = new FileDownLoadUtil(url,
                fileName, mActivity,
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
        fileDownLoadUtil.downLoad(isBase64, isXml);
    }
}
