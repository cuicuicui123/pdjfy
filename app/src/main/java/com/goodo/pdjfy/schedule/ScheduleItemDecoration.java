package com.goodo.pdjfy.schedule;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.goodo.pdjfy.base.AppContext;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class ScheduleItemDecoration extends RecyclerView.ItemDecoration {
    private int mWidth;

    public ScheduleItemDecoration(int width) {
        mWidth = width;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, mWidth, 0);
    }
}
