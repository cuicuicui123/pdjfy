package com.goodo.app.util;


import android.content.Context;
import android.os.Environment;
import android.util.Base64;

import com.goodo.app.rxjava.HttpMethods;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

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

    /**
     * 下载字节流文件
     */
    public void downLoad(final boolean isBase64, final boolean isXml) {
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
                            if (isBase64) {
                                saveBase64File(body.bytes(), file, isXml);
                            } else {
                                saveFile(body.bytes(), file);
                            }
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

    /**
     * 保存字节流文件
     * @param bytes
     * @param file
     */
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

    /**
     * 保存base64编码文件
     * @param bytes
     * @param file
     * @param isXml 是否是xml格式
     */
    private void saveBase64File(byte[] bytes, File file, boolean isXml){
        if (!isXml) {
            try {
                JSONObject jsonObject = new JSONObject(new String(bytes));
                JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                String base64Data = Goodo.getString("Base64Data");
                byte[] bytesDecode = Base64.decode(base64Data, Base64.DEFAULT);
                saveFile(bytesDecode, file);
            } catch (JSONException e) {
                e.printStackTrace();
                mOnDownLoadFailListener.downLoadFail();
            }
        } else {
            String response = new String(bytes);
            StringReader reader = new StringReader(response);
            InputSource source = new InputSource(reader);
            SAXBuilder builder = new SAXBuilder();
            try {
                Document document = builder.build(source);
                Element root = document.getRootElement();
                String base64Data = root.getAttributeValue("File");
                byte[] bytesDecode = Base64.decode(base64Data, Base64.DEFAULT);
                saveFile(bytesDecode, file);
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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
