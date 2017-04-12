package com.goodo.pdjfy.homepage.presenter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.homepage.model.HomePageTopListBean;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.util.List;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 首页轮播图adapter
 */

public class HomePagePagerAdapter extends PagerAdapter {
    private List<HomePageTopListBean> mBeanList;
    private int mImageWidth;
    private int mImageHeight;
    private AppContext mAppContext;
    private DisplayImageOptions mOptions;
    private OnItemClick mOnItemClick;

    public HomePagePagerAdapter(List<HomePageTopListBean> beanList) {
        mBeanList = beanList;
        mAppContext = AppContext.getInstance();
        mImageWidth = mAppContext.getWindowWidth();
        mImageHeight = mImageWidth * 9 / 16;
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnFail(R.drawable.pic_picture_failed)
                .displayer(new SimpleBitmapDisplayer())
                .considerExifParams(true)
                .setImageSize(new ImageSize(mImageWidth, mImageHeight))
                .build();
    }

    @Override
    public int getCount() {
        return mBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mAppContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(
                HttpMethods.BASE_URL + mBeanList.get(position).getPicUrl(), imageView, mOptions);
        container.addView(imageView, mImageWidth, mImageHeight);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick(mBeanList.get(position).getContents_ID());
                }
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /**
     * 点击事件
     */
    public void setOnItemClickListener(OnItemClick onItemClickListener){
        mOnItemClick = onItemClickListener;
    }

    public interface OnItemClick{
        void onItemClick(String contentId);
    }

}
