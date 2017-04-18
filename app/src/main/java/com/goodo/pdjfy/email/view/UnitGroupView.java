package com.goodo.pdjfy.email.view;

import android.widget.LinearLayout;

import com.goodo.pdjfy.email.model.UnitGroupBean;

import java.util.List;

/**
 * Created by Cui on 2016/12/29.
 * 单位群组页面接口
 */

public interface UnitGroupView {
    void getResponse(List<UnitGroupBean> list, LinearLayout ll);
}
