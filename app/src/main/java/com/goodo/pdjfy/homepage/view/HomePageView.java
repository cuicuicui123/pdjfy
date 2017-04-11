package com.goodo.pdjfy.homepage.view;

import com.goodo.pdjfy.homepage.model.HomePageTopListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 首页界面接口
 */

public interface HomePageView {
    void getTopListBeen(List<HomePageTopListBean> list);
}
