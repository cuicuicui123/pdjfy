package com.goodo.app.email.presenter;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.view.EmailListView;
import com.goodo.app.rxjava.CacheSubscriber;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class OuterEmailListSendPresenterImpl extends BaseEmailListPresenterImpl {
    private int mId;

    public OuterEmailListSendPresenterImpl(EmailListView emailListView, BaseActivity activity, int id) {
        super(emailListView, activity);
        mId = id;
        KEY_LIST = "getOuterEmailSendList";
    }

    @Override
    public void getEmailList(int page, int size) {
        mPage = page;
        mPageSize = size;
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_LIST + mId + page + size) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData, false);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response, true);
            }
        };
        mHttpMethods.getOuterEmailSendList(page, size, mId, cacheSubscriber);
    }
}
