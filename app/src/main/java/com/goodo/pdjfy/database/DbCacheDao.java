package com.goodo.pdjfy.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2016/7/7.
 * 对cache执行操作，存入缓存或者更新缓存
 */

public class DbCacheDao {
    private DbCacheOpenHelper mCacheOpenHelper;
    private SimpleDateFormat mFormat;

    public DbCacheDao(Context context) {
        mCacheOpenHelper = new DbCacheOpenHelper(context, "dbcache.db", null, 1);
        mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void insert(CacheObject cacheObject){//插入记录
        SQLiteDatabase db = mCacheOpenHelper.getWritableDatabase();//取得数据库操作
        db.execSQL("insert into cache (url, content, time) values(?, ?, ?)",
                new Object[]{cacheObject.getUrl(), cacheObject.getContent(), cacheObject.getDateTime()});
        db.close();//关闭数据库操作
    }

    public void upDate(CacheObject cacheObject){//更新记录
        SQLiteDatabase db = mCacheOpenHelper.getWritableDatabase();//取得数据库操作
        db.execSQL("update cache set content = ?, time = ? where url = ?",
                new Object[]{cacheObject.getContent(), cacheObject.getDateTime(), cacheObject.getUrl()});
        db.close();
    }

    public void deleteAll(){//清空缓存

    }

    public boolean isExist(CacheObject cacheObject){//判断此url对应的数据是否在数据库中存在，存在执行更新，不存在执行插入
        Boolean flag = false;
        SQLiteDatabase db = mCacheOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cache", new String[]{});
        List<CacheObject> list = new ArrayList<>();
        while (cursor.moveToNext()){
            CacheObject cacheObject1 = new CacheObject();
            cacheObject1.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            cacheObject1.setContent(cursor.getString(cursor.getColumnIndex("content")));
            cacheObject1.setDateTime(cursor.getString(cursor.getColumnIndex("time")));
            list.add(cacheObject1);
        }
        for(int i = 0;i < list.size();i ++){
            CacheObject cacheObject1 = list.get(i);
            if(cacheObject1.getUrl().equals(cacheObject.getUrl())){
                flag = true;
                break;
            }
        }
        cursor.close();
        db.close();
        return flag;
    }

    public SimpleDateFormat getFormat(){//获得format
        return mFormat;
    }

    public void handleCacheObject(CacheObject cacheObject){//对每一个缓存对象处理
        if(isExist(cacheObject)){
            upDate(cacheObject);
        }else{
            insert(cacheObject);
        }
    }

    public CacheObject getCacheObjectByUrl(String url){//通过url，在数据库中查询对应的缓存
        SQLiteDatabase db = mCacheOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cache where url = ?", new String[]{url});
        CacheObject cacheObject = new CacheObject();
        while (cursor.moveToNext()){
            cacheObject.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            cacheObject.setContent(cursor.getString(cursor.getColumnIndex("content")));
            cacheObject.setDateTime(cursor.getString(cursor.getColumnIndex("time")));
        }
        cursor.close();
        db.close();
        return  cacheObject;
    }



}
