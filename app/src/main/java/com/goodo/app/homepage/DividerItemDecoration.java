package com.goodo.app.homepage;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goodo.app.R;
import com.goodo.app.base.AppContext;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description RecyclerView水平分割线
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private AppContext mAppContext;
    private Drawable mDivider;

    public DividerItemDecoration() {
        mAppContext = AppContext.getInstance();
        mDivider = mAppContext.getResources().getDrawable(R.drawable.divider);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontalLine(c, parent, state);
    }

    //画横线, 这里的parent其实是显示在屏幕显示的这部分
    public void drawHorizontalLine(Canvas c, RecyclerView parent, RecyclerView.State state){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++){
            final View child = parent.getChildAt(i);

            //获得child的布局信息
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //画横线，就是往下偏移一个分割线的高度
        outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
    }
}
