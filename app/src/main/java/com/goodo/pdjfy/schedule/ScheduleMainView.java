package com.goodo.pdjfy.schedule;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.schedule.model.ScheduleBean;
import com.goodo.pdjfy.util.DataTransform;
import com.goodo.pdjfy.util.MyConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class ScheduleMainView extends View {
    private Context mContext;
    private Surface mSurface;
    private Calendar mCalendar;
    private Date mCurDate;
    private SimpleDateFormat mDateFormat;
    private List<ScheduleBean>[] mAllDayBeanLists;
    private List<ScheduleBean>[] mMorningBeanLists;
    private List<ScheduleBean>[] mAfterNoonBeanLists;

    private List<ScheduleBean>[][] mScheduleBeanLists;
    private int mRowNum = 7;
    private int mColumnNum = 3;
    private int mAllDayPosition = 0;
    private int mMorningPosition = 1;
    private int mAfterNoonPosition = 2;

    private OnItemClickListener mOnItemClickListener;

    public ScheduleMainView(Context context) {
        super(context);
        init(context);
    }

    public ScheduleMainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScheduleMainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mSurface = new Surface();
        mSurface.init();
        mCalendar = Calendar.getInstance();
        mCurDate = mCalendar.getTime();
        mDateFormat = MyConfig.getDateFormat();
        initScheduleBeanLists();
    }

    private void initScheduleBeanLists(){
        mAllDayBeanLists = new List[mRowNum];
        mMorningBeanLists = new List[mRowNum];
        mAfterNoonBeanLists = new List[mRowNum];
        mScheduleBeanLists = new List[mColumnNum][mRowNum];
        mScheduleBeanLists[0] = mAllDayBeanLists;
        mScheduleBeanLists[1] = mMorningBeanLists;
        mScheduleBeanLists[2] = mAfterNoonBeanLists;
        for (int i = 0;i < mRowNum;i ++) {
            mAllDayBeanLists[i] = new ArrayList<>();
            mMorningBeanLists[i] = new ArrayList<>();
            mAfterNoonBeanLists[i] = new ArrayList<>();
        }
    }


    /**
     * 获得总高度和宽度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawColumnLine(mSurface.mColumnGradientDrawable, canvas, mSurface.mCellWidth, 0, mSurface.mHeight);
        drawWeek(canvas);
        drawAllDay(canvas);
        drawMorning(canvas);
        drawAfternoon(canvas);
    }

    private void drawAfternoon(Canvas canvas) {
        drawVerticalText("下午", canvas, mSurface.mTimePaint, 0, mSurface.mCellWidth,
                mSurface.mWeekHeight + mSurface.mCellHeight * 3, mSurface.mWeekHeight + mSurface.mCellHeight * 5,
                mSurface.mWeekMargin);
        for (List<ScheduleBean> list : mAfterNoonBeanLists) {
            int size = list.size();
            for (int i = 0;i < size && i < 2;i ++) {
                ScheduleBean bean = list.get(i);
                drawCornerBac(bean, canvas,
                        (int) ((bean.getRow() + 1) * mSurface.mCellWidth + mSurface.mWeekMargin / 2),
                        (int) (mSurface.mWeekHeight + mSurface.mCellHeight * (i + 3) + mSurface.mWeekMargin / 2),
                        (int) ((bean.getRow() + 2) * mSurface.mCellWidth - mSurface.mWeekMargin / 2),
                        (int) (mSurface.mWeekHeight + mSurface.mCellHeight * (i + 4) - mSurface.mWeekMargin / 2));
                drawContentText(canvas, bean, mSurface.mWeekHeight + mSurface.mWeekMargin / 2 + mSurface.mCellHeight * (i + 3),
                        mSurface.mWeekHeight + mSurface.mCellHeight * (i + 4) - mSurface.mWeekMargin / 2);
            }
        }
        drawRowLine(mSurface.mGradientDrawable, canvas, 0, mSurface.mWeekHeight + mSurface.mCellHeight * 5);
    }

    private void drawMorning(Canvas canvas) {
        drawVerticalText("上午", canvas, mSurface.mTimePaint, 0, mSurface.mCellWidth,
                mSurface.mWeekHeight + mSurface.mCellHeight, mSurface.mWeekHeight + mSurface.mCellHeight * 3,
                mSurface.mWeekMargin);
        for (List<ScheduleBean> list : mMorningBeanLists) {
            int size = list.size();
            for (int i = 0;i < size && i < 2;i ++) {
                ScheduleBean bean = list.get(i);
                drawCornerBac(bean, canvas,
                        (int) ((bean.getRow() + 1) * mSurface.mCellWidth + mSurface.mWeekMargin / 2),
                        (int) (mSurface.mWeekHeight + mSurface.mCellHeight * (i + 1) + mSurface.mWeekMargin / 2),
                        (int) ((bean.getRow() + 2) * mSurface.mCellWidth - mSurface.mWeekMargin / 2),
                        (int) (mSurface.mWeekHeight + mSurface.mCellHeight * (i + 2) - mSurface.mWeekMargin / 2));
                drawContentText(canvas, bean, mSurface.mWeekHeight + mSurface.mWeekMargin / 2 + mSurface.mCellHeight * (i + 1),
                        mSurface.mWeekHeight + mSurface.mCellHeight * (i + 2) - mSurface.mWeekMargin / 2);
            }
        }
        drawRowLine(mSurface.mGradientDrawable, canvas, 0, mSurface.mWeekHeight + mSurface.mCellHeight * 3);
    }

    private void drawAllDay(Canvas canvas) {
        drawVerticalText("全天", canvas, mSurface.mTimePaint, 0, mSurface.mCellWidth,
                mSurface.mWeekHeight, mSurface.mWeekHeight + mSurface.mCellHeight, mSurface.mWeekMargin);
        for (List<ScheduleBean> list : mAllDayBeanLists) {
            int size = list.size();
            if (size > 0) {
                ScheduleBean scheduleBean = list.get(0);
                drawCornerBac(scheduleBean, canvas,
                        (int) ((scheduleBean.getRow() + 1) * mSurface.mCellWidth + mSurface.mWeekMargin / 2),
                        (int) (mSurface.mWeekHeight + mSurface.mWeekMargin / 2),
                        (int) ((scheduleBean.getRow() + 2) * mSurface.mCellWidth - mSurface.mWeekMargin / 2),
                        (int) (mSurface.mWeekHeight + mSurface.mCellHeight - mSurface.mWeekMargin / 2));
                drawContentText(canvas, scheduleBean, mSurface.mWeekHeight + mSurface.mWeekMargin / 2,
                        mSurface.mWeekHeight + mSurface.mCellHeight - mSurface.mWeekMargin / 2);
            }
        }
        drawRowLine(mSurface.mGradientDrawable, canvas, 0, mSurface.mWeekHeight + mSurface.mCellHeight);
    }

    private void drawContentText(Canvas canvas, ScheduleBean scheduleBean, float top, float bottom) {
        List<String> stringList = new ArrayList<>();
        stringList.add(scheduleBean.getBeginTime());
        stringList.add(scheduleBean.getWork());
        drawTextOverCellWidth(stringList, canvas,
                (scheduleBean.getRow() + 1) * mSurface.mCellWidth + mSurface.mWeekMargin / 2,
                top,
                (scheduleBean.getRow() + 2) * mSurface.mCellWidth - mSurface.mWeekMargin / 2,
                bottom,
                mSurface.mContentPaint);
    }

    /**
     * 超出一行宽度换行文字绘制
     * 超出高度 ..
     * @param textList
     * @param canvas
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param paint
     */
    private void drawTextOverCellWidth(List<String> textList, Canvas canvas, float left, float top, float right, float bottom, Paint paint) {
        float height = paint.measureText("上");
        int lines = (int) ((bottom - top) / height);
        int line = 0;
        float allWidth = right - left;
        for (String text : textList) {
            char[] chars = text.toCharArray();
            float width = 0;
            String newText = "";
            for (int i = 0;i < chars.length;i ++) {
                char c = chars[i];
                width = width + paint.measureText(String.valueOf(c));
                if (width > allWidth) {
                    float newTextWidth = paint.measureText(newText);
                    line ++;
                    if (line + 1 > lines) {//超出高度..
                        char[] newChars = newText.toCharArray();
                        int len = newChars.length;
                        newChars[len - 1] = '.';
                        newText = String.valueOf(newChars) + ".";
                        canvas.drawText(newText, left + (allWidth - newTextWidth) / 2, (top + height * line), paint);
                        return;
                    }
                    canvas.drawText(newText, left + (allWidth - newTextWidth) / 2, (top + height * line), paint);
                    width = 0;
                    newText = "";
                    i --;
                } else {
                    newText = newText + c;
                }
            }
            float newTextWidth = paint.measureText(newText);
            line ++;
            canvas.drawText(newText, left + (allWidth - newTextWidth) / 2, (top + height * line), paint);
        }
    }

    private void drawWeek(Canvas canvas) {
        int len = mSurface.mWeeks.length;
        Date baseDate = mCalendar.getTime();
        for (int i = 0;i < len;i ++) {
            mCalendar.add(Calendar.WEEK_OF_YEAR, i == len - 1 ? 1 : 0);
            mCalendar.set(Calendar.DAY_OF_WEEK, i + 2);
            boolean isToday = mCalendar.getTime().compareTo(mCurDate) == 0;
            Paint paint = isToday ? mSurface.mTodayPaint : mSurface.mWeekPaint;
            float height = paint.measureText("周");
            float width = height * 2;
            canvas.drawText(mSurface.mWeeks[i], (i + 1) * mSurface.mCellWidth + (mSurface.mCellWidth - width) / 2,
                    mSurface.mWeekHeight / 2 - mSurface.mWeekMargin / 2, paint);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);
            int month = mCalendar.get(Calendar.MONTH) + 1;
            String dayString = day == 1 ? month + "月" : day + "日";
            float dayWidth = paint.measureText(dayString);
            canvas.drawText(dayString, (i + 1) * mSurface.mCellWidth + (mSurface.mCellWidth - dayWidth) / 2,
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
        drawable.setBounds((int) left, (int) (top - mSurface.mLineHeight), (int) mSurface.mWidth, (int) top);
        drawable.draw(canvas);
    }

    /**
     * 绘制灰色竖线
     * @param drawable
     * @param canvas
     * @param left
     * @param top
     * @param bottom
     */
    private void drawColumnLine(Drawable drawable, Canvas canvas, float left, float top, float bottom){
        drawable.setBounds((int) (left - mSurface.mLineHeight), (int) top, (int) left, (int) bottom);
        drawable.draw(canvas);
    }

    /**
     * 绘制垂直的文字
     * @param text
     * @param paint
     * @param left
     * @param right
     * @param top
     * @param bottom
     */
    private void drawVerticalText(String text, Canvas canvas, Paint paint, float left, float right, float top, float bottom, float margin){
        int len = text.length();
        float textHeight = paint.measureText(text);
        float textTop = top + (bottom - top - textHeight - margin * (len - 1)) / 2;
        float textSize = paint.measureText("上");
        float textLeft = left + (right - left - textSize) / 2;

        for (int i = 0;i < len;i ++) {
            String c = text.charAt(i) + "";
            canvas.drawText(c, textLeft, textTop + textSize * (i + 1) + margin * i, paint);
        }
    }

    private void drawCornerBac(ScheduleBean scheduleBean, Canvas canvas, int left, int top, int right, int bottom){
        Drawable drawable;
        switch (scheduleBean.getType()) {
            case MyConfig.SCHEDULE_TYPE_COLLEGE:
                drawable = mSurface.mCollegeCornerBacDrawable;
                break;
            case MyConfig.SCHEDULE_TYPE_DEPART:
                drawable = mSurface.mDepartCornerBacDrawable;
                break;
            case MyConfig.SCHEDULE_TYPE_PERSON:
                drawable = mSurface.mPersonCornerBacDrawable;
                break;
            default:
                drawable = mSurface.mPersonCornerBacDrawable;
                break;
        }
        drawable.setBounds(left, top, right ,bottom);
        drawable.draw(canvas);
    }

    public String getYearAndMonth(){
        int month = mCalendar.get(Calendar.MONTH) + 1;
        return mCalendar.get(Calendar.YEAR) + "年" + month + "月";
    }

    public String getBeginDay(){
        Date baseDate = mCalendar.getTime();
        mCalendar.set(Calendar.DAY_OF_WEEK, 2);
        String beginDay = mDateFormat.format(mCalendar.getTime());
        mCalendar.setTime(baseDate);
        return beginDay;
    }

    public String getEndDay(){
        Date baseDate = mCalendar.getTime();
        mCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        mCalendar.set(Calendar.DAY_OF_WEEK, 1);
        String endDay = mDateFormat.format(mCalendar.getTime());
        mCalendar.setTime(baseDate);
        return endDay;
    }

    public String clickPre(){
        mCalendar.add(Calendar.WEEK_OF_YEAR, -1);
        invalidate();
        return getYearAndMonth();
    }

    public String clickNext(){
        mCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        invalidate();
        return getYearAndMonth();
    }

    public void setScheduleBeanList(List<ScheduleBean> list) {
        clearScheduleBeanLists();
        for (ScheduleBean bean:list) {
            getSchedulePosition(bean);
        }
        invalidate();
    }

    private void getSchedulePosition(ScheduleBean bean) {
        String dateString = DataTransform.transform(bean.getDate());
        try {
            Date baseDate = mCalendar.getTime();
            mCalendar.setTime(mDateFormat.parse(dateString));
            int day = mCalendar.get(Calendar.DAY_OF_WEEK);
            bean.setRow(day == 1 ? 6 : day - 2);
            mCalendar.setTime(baseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (bean.isIsAllDay()) {
            bean.setColumn(mAllDayPosition);
            mAllDayBeanLists[bean.getRow()].add(bean);
        } else if (bean.getBeginTime().compareTo(MyConfig.NOON_TIME) < 0){
            bean.setColumn(mMorningPosition);
            mMorningBeanLists[bean.getRow()].add(bean);
        } else {
            bean.setColumn(mAfterNoonPosition);
            mAfterNoonBeanLists[bean.getRow()].add(bean);
        }
    }

    private void clearScheduleBeanLists(){
        for (int i = 0;i < mRowNum;i ++) {
            mAllDayBeanLists[i].clear();
            mMorningBeanLists[i].clear();
            mAfterNoonBeanLists[i].clear();
        }
    }

    float mStartX;
    float mStartY;
    float mEndX;
    float mEndY;
    Runnable mLongPressRunnable;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = event.getX();
                mStartY = event.getY();
                mLongPressRunnable = new Runnable() {
                    @Override
                    public void run() {

                    }
                };
                postDelayed(mLongPressRunnable, ViewConfiguration.getLongPressTimeout());
                break;
            case MotionEvent.ACTION_UP:
                removeCallbacks(mLongPressRunnable);
                mEndX = event.getX();
                mEndY = event.getY();
                if (Math.abs(mEndX - mStartX) < 20 && Math.abs(mEndY - mStartY) < 20) {
                    findClickScheduleBean(mStartX, mStartY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getX() - mStartX) > 20 || Math.abs(event.getY() - mStartY) > 20) {
                    removeCallbacks(mLongPressRunnable);
                    return false;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                removeCallbacks(mLongPressRunnable);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 根据计算得到的scheduleBean的携带的位置信息得到点击的scheduleBean
     */
    private void findClickScheduleBean(float x, float y) {
        if (x > mSurface.mCellWidth && y > mSurface.mWeekHeight) {
            int indexX = (int) ((x - mSurface.mCellWidth) / mSurface.mCellWidth);
            int indexY = (int) ((y - mSurface.mWeekHeight) / mSurface.mCellHeight);
            int position;
            if (indexY == mAllDayPosition) {
                position = mAllDayPosition;
            } else if (indexY > 0 && indexY <= 2) {
                position = mMorningPosition;
                indexY = indexY - 1;
            } else {
                position = mAfterNoonPosition;
                indexY = indexY - 3;
            }
            List<ScheduleBean> list  = mScheduleBeanLists[position][indexX];
            if (list.size() > 0) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(list, indexY);
                }
            }
        }
    }

    class Surface {
        float mWidth;
        float mHeight;
        float mCellWidth;
        float mWeekHeight;
        float mCellHeight;

        float mTextSizeWeek;
        float mTextSizeTitle;
        float mTextSizeContent;
        float mLineHeight;
        float mWeekMargin;

        GradientDrawable mGradientDrawable;
        GradientDrawable mColumnGradientDrawable;
        Drawable mPersonCornerBacDrawable;
        Drawable mDepartCornerBacDrawable;
        Drawable mCollegeCornerBacDrawable;

        int mBacColor;
        int mBlack;
        int mTodayBacColor;
        int mBlue;
        int mWhite;

        Paint mBacPaint;
        Paint mWeekPaint;
        Paint mTodayPaint;
        Paint mTodayBacPaint;
        Paint mTimePaint;
        Paint mContentPaint;

        AppContext mAppContext;

        String[] mWeeks = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};


        public void init(){
            mAppContext = AppContext.getInstance();
            mWidth = mAppContext.getWindowWidth();
            mHeight = mAppContext.getWindowHeight() - getResources().getDimension(R.dimen.bar_height_bottom) - getResources().getDimension(R.dimen.bar_height_title) - getResources().getDimension(R.dimen.elevation_app_bar) - getResources().getDimension(R.dimen.elevation_bottom_bar);
            mTextSizeTitle = getResources().getDimension(R.dimen.text_size_content);
            mTextSizeWeek = getResources().getDimension(R.dimen.text_size_week);
            mTextSizeContent = getResources().getDimension(R.dimen.text_size_week);
            mLineHeight = getResources().getDimension(R.dimen.divider_height);
            mWeekMargin = getResources().getDimension(R.dimen.margin_content);
            mCellWidth = mWidth / 8f;
            mWeekHeight = mHeight / 11f;
            mCellHeight = mWeekHeight * 2f;

            mGradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_grey);
            mColumnGradientDrawable = (GradientDrawable) getResources().getDrawable(R.drawable.gradient_grey_column);
            mPersonCornerBacDrawable = getResources().getDrawable(R.drawable.corner_week_grey_bac);
            mDepartCornerBacDrawable = getResources().getDrawable(R.drawable.corner_week_blue_bac);
            mCollegeCornerBacDrawable = getResources().getDrawable(R.drawable.corner_week_red_bac);

            mTodayBacColor = getResources().getColor(R.color.blue_bac);

            mBacColor = getResources().getColor(R.color.grey_login_bac);
            mBacPaint = new Paint();
            mBacPaint.setColor(mBacColor);

            mBlack = getResources().getColor(R.color.black);
            mWeekPaint = new Paint();
            mWeekPaint.setColor(mBlack);
            mWeekPaint.setTextSize(mTextSizeWeek);
            mWeekPaint.setAntiAlias(true);

            mBlue = getResources().getColor(R.color.blue);
            mTodayPaint = new Paint();
            mTodayPaint.setColor(mBlue);
            mTodayPaint.setTextSize(mTextSizeTitle);
            mTodayPaint.setAntiAlias(true);

            mTodayBacPaint = new Paint();
            mTodayBacPaint.setColor(mTodayBacColor);

            mTimePaint = new Paint();
            mTimePaint.setColor(mBlack);
            mTimePaint.setTextSize(mTextSizeTitle);
            mTimePaint.setAntiAlias(true);

            mWhite = getResources().getColor(R.color.white);
            mContentPaint = new Paint();
            mContentPaint.setColor(mWhite);
            mContentPaint.setTextSize(mTextSizeContent);
            mContentPaint.setAntiAlias(true);

        }
    }

    public interface OnItemClickListener{
        void onItemClick(List<ScheduleBean> list, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }


}
