package com.goodo.app.rxjava;

/**
 * Created by Cui on 2017/4/25.
 *
 * @Description
 */

public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
