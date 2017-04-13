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

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class ScheduleView extends View {
    private Context mContext;
    private Surface mSurface;
    private Calendar mCalendar;
    private Date mCurDate;

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
        mCalendar = java.util.Calendar.getInstance();
        mCurDate = mCalendar.getTime();
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
        drawWeek(canvas);
        drawAllDay(canvas);
        drawMorning(canvas);
        drawAfternoon(canvas);
    }

    private void drawAfternoon(Canvas canvas) {
        int len = mSurface.mWeeks.length;
        for (int i = 0;i < len;i ++) {
            drawColumnLine(mSurface.mColumnGradientDrawable, canvas, (i + 1) * mSurface.mCellWidth,
                    mSurface.mWeekHeight + mSurface.mCellHeight * 3, mSurface.mWeekHeight + mSurface.mCellHeight * 5);
        }
        drawRowLine(mSurface.mGradientDrawable, canvas, mSurface.mCellWidth, mSurface.mWeekHeight + mSurface.mCellHeight * 4 + mSurface.mLineHeight);
        drawRowLine(mSurface.mGradientDrawable, canvas, 0, mSurface.mWeekHeight + mSurface.mCellHeight * 5 + mSurface.mLineHeight);
        drawTextColumn(canvas, mSurface.mTodayPaint, 0, mSurface.mWeekHeight + mSurface.mCellHeight * 3, mSurface.mCellWidth, mSurface.mWeekHeight + mSurface.mCellHeight * 5, "下午");

    }

    private void drawMorning(Canvas canvas) {
        int len = mSurface.mWeeks.length;
        for (int i = 0;i < len;i ++) {
            drawColumnLine(mSurface.mColumnGradientDrawable, canvas, (i + 1) * mSurface.mCellWidth,
                    mSurface.mWeekHeight + mSurface.mCellHeight, mSurface.mWeekHeight + mSurface.mCellHeight * 3);
        }
        drawRowLine(mSurface.mGradientDrawable, canvas, mSurface.mCellWidth, mSurface.mWeekHeight + mSurface.mCellHeight * 2 + mSurface.mLineHeight);
        drawRowLine(mSurface.mGradientDrawable, canvas, 0, mSurface.mWeekHeight + mSurface.mCellHeight * 3 + mSurface.mLineHeight);
        drawTextColumn(canvas, mSurface.mTodayPaint, 0, mSurface.mWeekHeight + mSurface.mCellHeight, mSurface.mCellWidth, mSurface.mWeekHeight + mSurface.mCellHeight * 3, "上午");
    }

    private void drawTextColumn(Canvas canvas, Paint paint, float left, float top, float right, float bottom, String text){
        float width = paint.measureText("上");
        float height = paint.measureText(text);
        float textTop = top + (bottom - top - height) / 2;
        float textLeft = left + (right - left - width) / 2;
        for (int i = 0;i < text.length();i ++) {
            String c = text.charAt(i) + "";
            canvas.drawText(c, textLeft, textTop + i * height, paint);
        }
    }

    private void drawAllDay(Canvas canvas) {
        int len = mSurface.mWeeks.length;
        for (int i = 0;i < len;i ++) {
            drawColumnLine(mSurface.mColumnGradientDrawable, canvas, (i + 1) * mSurface.mCellWidth,
                    mSurface.mWeekHeight, mSurface.mWeekHeight + mSurface.mCellHeight);
        }
        drawRowLine(mSurface.mGradientDrawable, canvas, 0, mSurface.mWeekHeight + mSurface.mCellHeight + mSurface.mLineHeight);
        drawTextColumn(canvas, mSurface.mTodayPaint, 0, mSurface.mWeekHeight, mSurface.mCellWidth, mSurface.mWeekHeight + mSurface.mCellHeight, "全天");

    }

    /**
     * 绘制周次
     * @param canvas
     */
    private void drawWeek(Canvas canvas) {
        Date baseDate = mCalendar.getTime();
        int len = mSurface.mWeeks.length;
        int month = mCalendar.get(Calendar.MONTH) + 1;
        String monthStr = month + "月";
        for (int i = 0;i < len;i ++) {
            Paint paint;
            mCalendar.add(Calendar.WEEK_OF_YEAR, i == len - 1 ? 1 : 0);
            mCalendar.set(Calendar.DAY_OF_WEEK, i + 2);
            boolean isToday = mCalendar.getTime().compareTo(mCurDate) == 0;
            paint = isToday ? mSurface.mTodayPaint : mSurface.mWeekPaint;
            if (isToday) {
                canvas.drawRect((i + 1) * mSurface.mCellWidth, 0, (i + 2) * mSurface.mCellWidth,
                        mSurface.mWeekHeight, mSurface.mTodayBacPaint);
            }
            float height = paint.measureText("周");
            float width = height * 2;
            drawColumnLine(mSurface.mColumnGradientDrawable, canvas, (i + 1) * mSurface.mCellWidth, 0, mSurface.mWeekHeight);
            canvas.drawText(mSurface.mWeeks[i], (i + 1) * mSurface.mCellWidth + (mSurface.mCellWidth - width) / 2,
                    mSurface.mWeekHeight / 2 - mSurface.mWeekMargin / 2, paint);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            String date = day == 1 ? monthStr : day + "日";
            float dateWidth = paint.measureText(date);
            canvas.drawText(date, (i + 1) * mSurface.mCellWidth + (mSurface.mCellWidth - dateWidth) / 2,
                    mSurface.mWeekHeight / 2 + height + mSurface.mWeekMargin / 2, paint);
        }
        drawRowLine(mSurface.mGradientDrawable, canvas, 0, mSurface.mWeekHeight);
        mCalendar.setTime(baseDate);
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

    /**
     * 回执灰色竖线
     * @param drawable
     * @param canvas
     * @param left
     * @param top
     * @param bottom
     */
    private void drawColumnLine(Drawable drawable, Canvas canvas, float left, float top, float bottom){
        drawable.setBounds((int) left, (int) top, (int) (left + mSurface.mLineHeight), (int) bottom);
        drawable.draw(canvas);
    }

    public String getYearAndMonth(){
        int month = mCalendar.get(Calendar.MONTH) + 1;
        return mCalendar.get(Calendar.YEAR) + "年" + month + "月";
    }

    public void clickLeft(){
        mCalendar.add(Calendar.WEEK_OF_YEAR, -1);
        invalidate();
    }

    public void clickRight(){
        mCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        invalidate();
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
        float mWeekMargin;


        GradientDrawable mGradientDrawable;
        GradientDrawable mColumnGradientDrawable;

        int mBacColor;
        int mWeekColor;

        Paint mBacPaint;
        Paint mWeekPaint;
        Paint mTodayPaint;
        Paint mTodayBacPaint;

        AppContext mAppContext;

        String[] mWeeks = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};


        public void init(){
            mAppContext = AppContext.getInstance();
            mWidth = mAppContext.getWindowWidth();
            mHeight = (int) (mAppContext.getWindowHeight() - getResources().getDimension(R.dimen.bar_height_bottom)
                                - getResources().getDimension(R.dimen.bar_height_title));
            mTextSizeContent = mAppContext.getResources().getDimension(R.dimen.text_size_date);
            mTextSizeWeek = mAppContext.getResources().getDimension(R.dimen.text_size_week);
            mGradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_grey);
            mColumnGradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_grey_column);
            mLineHeight = getResources().getDimension(R.dimen.divider_height);
            mCellWidth = mWidth / 8;
            mWeekHeight = mHeight / 11;
            mCellHeight = mWeekHeight * 2;
            mWeekMargin = getResources().getDimension(R.dimen.margin_content);

            mBacColor = getResources().getColor(R.color.grey_login_bac);
            mBacPaint = new Paint();
            mBacPaint.setColor(mBacColor);

            mWeekColor = getResources().getColor(R.color.black);
            mWeekPaint = new Paint();
            mWeekPaint.setColor(mWeekColor);
            mWeekPaint.setTextSize(mTextSizeWeek);
            mWeekPaint.setAntiAlias(true);

            mTodayPaint = new Paint();
            mTodayPaint.setColor(mWeekColor);
            mTodayPaint.setTextSize(mTextSizeContent);
            mTodayPaint.setAntiAlias(true);

            mTodayBacPaint = new Paint();
            mTodayBacPaint.setColor(getResources().getColor(R.color.blue_bac));
        }
    }

}
