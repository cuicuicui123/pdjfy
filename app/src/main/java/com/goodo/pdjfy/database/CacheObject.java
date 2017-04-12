package com.goodo.pdjfy.database;

/**
 * Created by Cui on 2016/7/7.
 * 缓存bean
 * url为key，content为value
 */
public class CacheObject {
    private String mUrl;
    private String mContent;
    private String mDateTime;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        this.mDateTime = dateTime;
    }
}
