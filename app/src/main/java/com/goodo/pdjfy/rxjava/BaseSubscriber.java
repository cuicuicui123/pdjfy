package com.goodo.pdjfy.rxjava;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Cui on 2016/12/20.
 * rxJava主题父类，不需要实现onCompleted和onError时不用实现此方法
 */

public abstract class BaseSubscriber extends Subscriber<ResponseBody> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
