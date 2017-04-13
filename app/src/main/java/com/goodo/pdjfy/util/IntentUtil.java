package com.goodo.pdjfy.util;

import android.content.Intent;
import android.net.Uri;

import com.goodo.pdjfy.base.AppContext;

import java.io.File;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class IntentUtil {
    //android获取一个用于打开文件的intent
    public static Intent getFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, AppContext.isMeiZu() ? "file/*" : "*/*");//魅族手机用file
        return intent;
    }
}
