package com.goodo.pdjfy.homepage.presenter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public interface DownLoadFilePresenter {
    void downLoadFile(String url, String fileName, ProgressBar progressBar, View view, boolean isBase64);
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
