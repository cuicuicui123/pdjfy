package com.goodo.app.util;

import android.content.Intent;
import android.net.Uri;

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
        String extension = MyConfig.getFileExtension(file.getName());
        intent.setDataAndType(uri, extension);//魅族手机用file
        return intent;
    }

    /**
     * 选择文件
     */
    public static Intent getSelectFileIntent(){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("*/*");
        return it;
    }
}
