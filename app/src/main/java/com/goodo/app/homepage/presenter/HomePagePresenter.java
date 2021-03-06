package com.goodo.app.homepage.presenter;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 首页逻辑处理接口
 */

public interface HomePagePresenter {
    void getHomePageTopList(int topList);
    void startToNewsListActivity(int position);
    void startToPicDetailActivity(String contentId);
    void startToLoginActivity();
}
