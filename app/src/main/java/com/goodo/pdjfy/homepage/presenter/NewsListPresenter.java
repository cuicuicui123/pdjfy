package com.goodo.pdjfy.homepage.presenter;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 首页新闻逻辑处理接口
 */

public interface NewsListPresenter {
    void getNewsList(String news, int page, int pageSize, String keyword);
    void startToDetailActivity(String contentId, String title);
    void startToLoginActivity();
}
