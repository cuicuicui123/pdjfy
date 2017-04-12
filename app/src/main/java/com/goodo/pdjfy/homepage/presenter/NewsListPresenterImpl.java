package com.goodo.pdjfy.homepage.presenter;

import android.content.Intent;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.Flag;
import com.goodo.pdjfy.homepage.HomePageNewsDetailActivity;
import com.goodo.pdjfy.homepage.LoginActivity;
import com.goodo.pdjfy.homepage.model.NewsListBean;
import com.goodo.pdjfy.homepage.view.NewsListView;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
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

    private int mDbSize;
    private BaseActivity mActivity;
    private static String KEY_NEWS_LIST = "getHomePageNewsList";

    public NewsListPresenterImpl(NewsListView newsListView, BaseActivity activity) {
        mNewsListView = newsListView;
        mHttpMethods = HttpMethods.getInstance();
        mActivity = activity;
    }

    @Override
    public void getNewsList(String news, int page, int pageSize, String keyword) {
        CacheSubscriber subscriber = new CacheSubscriber(KEY_NEWS_LIST + news + page) {

            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData, false);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response, true);
            }
        };
        mHttpMethods.getHomePageNewsList(news, page, pageSize, keyword, subscriber);
    }

    @Override
    public void startToDetailActivity(String contentId, String title) {
        Intent it = new Intent(mActivity, HomePageNewsDetailActivity.class);
        it.putExtra(Flag.KEY_TITLE, title);
        it.putExtra(Flag.KEY_CONTENT_ID, contentId);
        mActivity.startActivity(it);
    }

    @Override
    public void startToLoginActivity() {
        Intent it = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(it);
    }

    private void handleResponse(String response, boolean hasNewInfo) {
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
            mDbSize = hasNewInfo ? 0 : list.size();
            mNewsListView.getNewsListBeanList(list, hasNewInfo, mDbSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
