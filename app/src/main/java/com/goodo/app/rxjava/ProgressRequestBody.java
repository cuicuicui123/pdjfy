package com.goodo.app.rxjava;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Cui on 2017/4/25.
 *
 * @Description
 */

public class ProgressRequestBody extends RequestBody {
    //实际的待包装请求体
    private RequestBody mRequestBody;
    //进度回调接口
    private ProgressRequestListener mProgressRequestListener;
    //包装完成的BufferedSink
    private BufferedSink mBufferedSink;

    public ProgressRequestBody(RequestBody requestBody, ProgressRequestListener progressRequestListener) {
        mRequestBody = requestBody;
        mProgressRequestListener = progressRequestListener;
    }

    /**
     * 重写调用实际的响应体的contentType
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    /**
     * 重写调用实际响应提的contentLength
     * @return contentLength
     * @throws IOException
     */
    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    /**
     * 重写进行写入
     * @param sink BufferedSink
     * @throws IOException
     */
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (mBufferedSink == null) {
            //包装
            mBufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(mBufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        mBufferedSink.flush();
    }

    /**
     * 写入，回调进度接口
     * @param sink
     * @return
     */
    private Sink sink(Sink sink){
        return new ForwardingSink(sink) {
            //当前写入字节数
            long byteWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                byteWritten += byteCount;
                //回调
                if (mProgressRequestListener != null) {
                    mProgressRequestListener.onRequestProgress(byteWritten, contentLength,
                            byteWritten == contentLength);
                }
            }
        };
    }

}
