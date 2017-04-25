package com.goodo.app.base;

/**
 * Created by m1888 on 2016/4/13.
 */

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.WindowManager;

import com.goodo.app.R;
import com.goodo.app.util.DensityUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @author linjizong
 * @created 2015-3-22
 */
public class AppContext extends Application {
    private static AppContext mAppContext = null;
    private Display mDisplay;
    private DensityUtil mDensityUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
        init();
    }

    public static AppContext getInstance() {
        return mAppContext;
    }

    /**
     * 初始化
     */
    private void init() {
        initImageLoader(getApplicationContext());
        if (mDisplay == null) {
            WindowManager windowManager = (WindowManager)
                    getSystemService(Context.WINDOW_SERVICE);
            mDisplay = windowManager.getDefaultDisplay();
        }
    }

    /**
     * 初始化ImageLoader
     * @param context
     */
    public void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 4);
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 100 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //修改连接超时时间5秒，下载超时时间5秒
        config.imageDownloader(new BaseImageDownloader(mAppContext, 5 * 1000, 5 * 1000));
        ImageLoader.getInstance().init(config.build());
    }

    /**
     * 返回uil设置信息
     * @param width
     * @return
     */
    public DisplayImageOptions getImageOptions(int width){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .setImageSize(new ImageSize(width, 0))
                .displayer(new SimpleBitmapDisplayer())
                .considerExifParams(true)
                .build();
    }

    public DisplayImageOptions getImageOptions(){
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnFail(R.drawable.pic_picture_failed)
                .displayer(new SimpleBitmapDisplayer())
                .considerExifParams(true)
                .build();
    }

    //获取当前屏幕的宽度
    public int getWindowWidth() {
        return mDisplay.getWidth();
    }
    //获取当前屏幕的高度
    public int getWindowHeight() {
        return mDisplay.getHeight();
    }
    //获取当前屏幕一半宽度
    public int getHalfWidth() {
        return mDisplay.getWidth() / 2;
    }
    //获取当前屏幕1/4宽度
    public int getQuarterWidth() {
        return mDisplay.getWidth() / 4;
    }
    //返回像素与密度转换的工具
    public DensityUtil getDensityUtil(){
        if (mDensityUtil == null) {
            mDensityUtil = new DensityUtil(this);
        }
        return mDensityUtil;
    }

    public static boolean isMeiZu(){
        return "Meizu".equals(android.os.Build.MANUFACTURER);
    }


}

