package com.goodo.pdjfy.news.presenter;

import com.goodo.pdjfy.news.model.NewsListBean;
import com.goodo.pdjfy.news.view.NewsListView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.rxjava.MySubscriber;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 新闻列表页逻辑实现类
 */

public class NewsListPresenterImpl implements NewsListPresenter {
    private NewsListView mNewsListView;
    private HttpMethods mHttpMethods;

    public NewsListPresenterImpl(NewsListView newsListView) {
        mNewsListView = newsListView;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getNewsList(String news, int page, int pageSize, String keyword) {
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    final List<NewsListBean> list = new ArrayList<>();
                    final Gson gson = new Gson();
                    JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                        @Override
                        public void judged(JSONObject jsonObject) throws JSONException {
                            NewsListBean bean = gson.fromJson(jsonObject.toString(), NewsListBean.class);
                            list.add(bean);
                        }
                    });
                    mNewsListView.getNewsListBeanList(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mHttpMethods.getHomePageNewsList(news, page, pageSize, keyword, subscriber);
    }
}
