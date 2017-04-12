package com.goodo.pdjfy.util;

import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description
 */

public class IntentUtil {
    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(File file)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }
}
