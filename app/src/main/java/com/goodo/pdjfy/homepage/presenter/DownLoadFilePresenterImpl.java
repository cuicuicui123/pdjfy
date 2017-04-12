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
import com.goodo.pdjfy.homepage.HomePageNewsDetailActivity;
import com.goodo.pdjfy.homepage.model.AttachBean;
import com.goodo.pdjfy.homepage.view.NewsAttachView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.util.Base64FileDownLoad;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class DownLoadFilePresenterImpl implements DownLoadFilePresenter {
    private NewsAttachView mNewsAttachView;
    private AppContext mAppContext;
    private BaseActivity mActivity;
    private AttachBean mAttachBean;
    private ProgressBar mProgressBar;

    public DownLoadFilePresenterImpl(NewsAttachView newsAttachView, BaseActivity activity) {
        mNewsAttachView = newsAttachView;
        mAppContext = AppContext.getInstance();
        mActivity = activity;
    }

    @Override
    public void downLoadFile(AttachBean attachBean, final ProgressBar progressBar) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                mAttachBean = attachBean;
                mProgressBar = progressBar;
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MyConfig.READ_STORAGE_CODE);
            } else {
                startDownLoad(attachBean, progressBar);
            }
        } else {
            startDownLoad(attachBean, progressBar);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MyConfig.READ_STORAGE_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownLoad(mAttachBean, mProgressBar);
            } else {
                Toast.makeText(mActivity, "需要读取文件权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startDownLoad(AttachBean attachBean, final ProgressBar progressBar) {
        Base64FileDownLoad base64FileDownLoad = new Base64FileDownLoad(attachBean.getUrl(), attachBean.getName(), mActivity,
                new Base64FileDownLoad.OnDownLoadSuccessListener() {
                    @Override
                    public void downLoadSuccess(File file) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent it = IntentUtil.getImageFileIntent(file);
                        mActivity.startActivity(it);
                    }
                },
                new Base64FileDownLoad.OnDownLoadFailListener() {
                    @Override
                    public void downLoadFail() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(mActivity, "下载失败", Toast.LENGTH_SHORT).show();
                    }
        });
        base64FileDownLoad.downLoad();
    }
}
