package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.view.EmailListView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class AllEmailListReceivePresenterImpl extends BaseEmailListPresenterImpl {
    public AllEmailListReceivePresenterImpl(EmailListView emailListView, BaseActivity activity) {
        super(emailListView, activity);
        KEY_LIST = "getAllEmailReceiveList";
    }

    @Override
    public void getEmailList(int page, int size) {
        mPage = page;
        mPageSize = size;
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_LIST + page + size) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData, false);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response, true);
            }
        };
        mHttpMethods.getAllEmailReceiveList(page, size, cacheSubscriber);
    }



}
