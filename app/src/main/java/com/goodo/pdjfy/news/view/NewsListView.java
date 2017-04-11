package com.goodo.pdjfy.news.view;

import com.goodo.pdjfy.news.model.NewsListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 首页新闻列表页面接口
 */

public interface NewsListView {
    void getNewsListBeanList(List<NewsListBean> list);
}
