package com.goodo.pdjfy.rxjava;


import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.database.CacheObject;
import com.goodo.pdjfy.database.DbCacheDao;

import java.io.IOException;
import java.util.Date;

import okhttp3.ResponseBody;

/**
 * Created by Cui on 2016/12/13.
 * 需要缓存的请求
 */

public abstract class CacheSubscriber extends BaseSubscriber {
    private String mCacheUrl;
    private DbCacheDao mDbCacheDao;
    private String mResponse;

    public CacheSubscriber(String cacheUrl) {
        mCacheUrl = cacheUrl;
        mDbCacheDao = new DbCacheDao(AppContext.getInstance());
    }

    @Override
    public void onStart() {
        //先从数据库中读取缓存
        CacheObject cacheObject = mDbCacheDao.getCacheObjectByUrl(mCacheUrl);
        mResponse = cacheObject.getContent();
        if (mResponse != null) {
            getCache(mResponse);
        }
    }

    @Override
    public void onNext(ResponseBody body) {
        try {
            String response = body.string();
            if (!response.equals(mResponse)) {
                CacheObject cacheObject = new CacheObject();
                cacheObject.setUrl(mCacheUrl);
                cacheObject.setContent(response);
                cacheObject.setDateTime(mDbCacheDao.getFormat().format(new Date()));
                mDbCacheDao.handleCacheObject(cacheObject);
                onResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void getCache(String cacheData);

    protected abstract void onResponse(String response);
}
