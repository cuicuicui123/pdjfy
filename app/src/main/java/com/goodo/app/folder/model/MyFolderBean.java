package com.goodo.app.folder.model;

import android.graphics.Bitmap;

/**
 * Created by Cui on 2016/10/19.
 * 文件夹bean
 */

public class MyFolderBean {
    int mIconResId;
    String mFileName;
    String mFilePath;
    boolean mIsPicture;
    Bitmap mImageBitmap;

    public Bitmap getImageBitmap() {
        return mImageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.mImageBitmap = imageBitmap;
    }

    public boolean isPicture() {
        return mIsPicture;
    }

    public void setPicture(boolean picture) {
        mIsPicture = picture;
    }

    public int getIconResId() {
        return mIconResId;
    }

    public void setIconResId(int iconResId) {
        this.mIconResId = iconResId;
    }

    public String getFileName() {
        return mFileName;
    }

    public void setFileName(String fileName) {
        this.mFileName = fileName;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        this.mFilePath = filePath;
    }
}
