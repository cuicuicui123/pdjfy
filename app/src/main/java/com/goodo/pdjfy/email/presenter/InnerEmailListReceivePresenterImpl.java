package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.view.EmailListView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class InnerEmailListReceivePresenterImpl extends BaseEmailListPresenterImpl {
    private int mReceiveClassifyId;

    public InnerEmailListReceivePresenterImpl(EmailListView emailListView, BaseActivity activity, int receiveClassifyId) {
        super(emailListView, activity);
        mReceiveClassifyId = receiveClassifyId;
        KEY_LIST = "";
    }

    @Override
    public void getEmailList(int page, int size) {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_LIST + mReceiveClassifyId + page + size) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData, false);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response, true);
            }
        };
        mHttpMethods.getInnerEmailReceiveList(page, size, mReceiveClassifyId, cacheSubscriber);
    }
}
