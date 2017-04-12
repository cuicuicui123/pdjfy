package com.goodo.pdjfy.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cui on 2016/7/7.
 * 创建缓存数据库
 */
public class DbCacheOpenHelper extends SQLiteOpenHelper {

    /**
     *
     * @param context
     * 上下文
     * @param name
     * 数据库名
     * @param factory
     * 选的数据库游标工厂类，当查询(query)被提交时，该对象会被调用来实例化一个游标。默认为null。
     * @param version
     * 数据库版本号
     */
    public DbCacheOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建cache表
        db.execSQL("create table cache(id integer primary key autoincrement, url text, content text, time datetime)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
