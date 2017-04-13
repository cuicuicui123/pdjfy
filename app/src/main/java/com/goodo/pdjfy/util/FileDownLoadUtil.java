package com.goodo.pdjfy.util;


import android.content.Context;
import android.os.Environment;

import com.goodo.pdjfy.rxjava.HttpMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 下载base64编码的文件
 */

public class FileDownLoadUtil {
    private String mUrl;
    private String mFileName;
    private String mPath;
    private HttpMethods mHttpMethods;
    private Context mContext;
    private OnDownLoadSuccessListener mOnDownLoadSuccessListener;
    private OnDownLoadFailListener mOnDownLoadFailListener;

    public FileDownLoadUtil(String mUrl, String mFileName, Context context
            , OnDownLoadSuccessListener onDownLoadSuccessListener, OnDownLoadFailListener onDownLoadFailListener) {
        this.mUrl = mUrl;
        this.mFileName = mFileName;
        mContext = context;
        initPath();
        mHttpMethods = HttpMethods.getInstance();
        mOnDownLoadSuccessListener = onDownLoadSuccessListener;
        mOnDownLoadFailListener = onDownLoadFailListener;
    }

    private void initPath() {
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdjfy/";
        File folder = new File(mPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public void downLoad() {
        final File file = new File(mPath + mFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Subscriber<ResponseBody> subscriber = new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            saveFile(body.bytes(), file);
                            mOnDownLoadSuccessListener.downLoadSuccess(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                            mOnDownLoadFailListener.downLoadFail();
                        }
                    }
                };
                mHttpMethods.downLoad(mUrl, subscriber);
            } catch (IOException e) {
                e.printStackTrace();
                mOnDownLoadFailListener.downLoadFail();
            }
        } else {
            if (mOnDownLoadSuccessListener != null) {
                mOnDownLoadSuccessListener.downLoadSuccess(file);
            }
        }

    }

    private void saveFile(byte[] bytes, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnDownLoadSuccessListener {
        void downLoadSuccess(File file);
    }

    public interface OnDownLoadFailListener{
        void downLoadFail();
    }


}
