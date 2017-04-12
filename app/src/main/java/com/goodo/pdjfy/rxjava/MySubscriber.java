package com.goodo.pdjfy.rxjava;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by Cui on 2016/12/13.
 * 不需要缓存的请求
 */

public abstract class MySubscriber extends BaseSubscriber {

    @Override
    public void onNext(ResponseBody body) {
        try {
            String response = body.string();
            onResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onResponse(String response);
}
