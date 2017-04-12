package com.goodo.pdjfy.homepage.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.HomePageDetailBean;
import com.goodo.pdjfy.homepage.view.HomePageNewsDetailView;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 首页图片详情页面
 */

public class HomePagePicDetailPresenterImpl extends BaseDetailPresenterImpl {

    public HomePagePicDetailPresenterImpl(HomePageNewsDetailView detailView, BaseActivity activity) {
        super(detailView, activity);
    }

    @Override
    protected void handleResponse(String response) {
        HomePageDetailBean bean = new HomePageDetailBean();
        bean.setContent(response);
        mDetailView.getNewsDetailBean(bean);
    }
}
