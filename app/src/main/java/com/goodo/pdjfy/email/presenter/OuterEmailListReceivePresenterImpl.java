package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.view.EmailListView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class OuterEmailListReceivePresenterImpl extends BaseEmailListPresenterImpl {
    private int mId;

    public OuterEmailListReceivePresenterImpl(EmailListView emailListView, BaseActivity activity, int id) {
        super(emailListView, activity);
        mId = id;
        KEY_LIST = "getOuterEmailReceiveList";
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
        mHttpMethods.getOuterEmailReceiveList(page, size, mId, cacheSubscriber);
    }
}
