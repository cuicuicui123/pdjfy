package com.goodo.pdjfy.homepage.presenter;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;

import com.goodo.pdjfy.homepage.model.AttachBean;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public interface DownLoadFilePresenter {
    void downLoadFile(AttachBean attachBean, ProgressBar progressBar, View view);
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
