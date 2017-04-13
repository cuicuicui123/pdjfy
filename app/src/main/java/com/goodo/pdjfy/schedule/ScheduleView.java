package com.goodo.pdjfy.schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class ScheduleView extends View {
    private Context mContext;
    private Surface mSurface;

    public ScheduleView(Context context) {
        super(context);
        init(context);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScheduleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mSurface = new Surface();
        mSurface.init();
    }

    /**
     * 获得总高度和宽度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mSurface.mWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mSurface.mHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, mSurface.mWidth, mSurface.mHeight, mSurface.mBacPaint);
        int len = mSurface.mWeeks.length;
        float height = mSurface.mWeekPaint.measureText("周");
        float width = height * 2;
        for (int i = 0;i < len;i ++) {
            canvas.drawText(mSurface.mWeeks[i], (i + 1) * mSurface.mCellWidth + (mSurface.mCellWidth - width) / 2,
                    (mSurface.mWeekHeight - height) / 2, mSurface.mWeekPaint);
        }


    }

    /**
     * 绘制灰色横线
     *
     * @param canvas 画布
     * @param left   横线X坐标
     * @param top    横线Y坐标
     */
    private void drawRowLine(Drawable drawable, Canvas canvas, float left, float top) {
        drawable.setBounds((int) left, (int) top, mSurface.mWidth, (int) (top + mSurface.mLineHeight));
        drawable.draw(canvas);
    }

    class Surface{
        int mWidth;
        int mHeight;
        int mCellWidth;
        int mWeekHeight;
        int mCellHeight;

        float mTextSizeWeek;
        float mTextSizeContent;
        float mLineHeight;


        GradientDrawable mGradientDrawable;
        GradientDrawable mColumnGradientDrawable;

        int mBacColor;
        int mWeekColor;

        Paint mBacPaint;
        Paint mWeekPaint;

        AppContext mAppContext;

        String[] mWeeks = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};


        public void init(){
            mAppContext = AppContext.getInstance();
            mWidth = mAppContext.getWindowWidth();
            mHeight = (int) (mAppContext.getWindowHeight() - getResources().getDimension(R.dimen.bar_height_bottom)
                                - getResources().getDimension(R.dimen.bar_height_title));
            mTextSizeContent = mAppContext.getResources().getDimension(R.dimen.text_size_date);
            mTextSizeWeek = mAppContext.getResources().getDimension(R.dimen.text_size_content);
            mGradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_grey);
            mColumnGradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_grey_column);
            mLineHeight = getResources().getDimension(R.dimen.divider_height);
            mCellWidth = mWidth / 8;
            mWeekHeight = mHeight / 11;
            mCellHeight = mWeekHeight * 2;

            mBacColor = getResources().getColor(R.color.grey_login_bac);
            mBacPaint = new Paint();
            mBacPaint.setColor(mBacColor);

            mWeekColor = getResources().getColor(R.color.black);
            mWeekPaint = new Paint();
            mWeekPaint.setColor(mWeekColor);
            mWeekPaint.setTextSize(mTextSizeWeek);
            mWeekPaint.setAntiAlias(true);
        }
    }

}
