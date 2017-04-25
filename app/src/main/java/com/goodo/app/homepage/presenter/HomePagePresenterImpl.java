package com.goodo.app.homepage.presenter;

import android.content.Intent;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.util.MyConfig;
import com.goodo.app.homepage.HomePagePicDetailActivity;
import com.goodo.app.homepage.LoginActivity;
import com.goodo.app.homepage.NewsListActivity;
import com.goodo.app.homepage.model.HomePageTopListBean;
import com.goodo.app.homepage.view.HomePageView;
import com.goodo.app.rxjava.CacheSubscriber;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.util.JudgeIsJsonArray;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.goodo.app.util.MyConfig.KEY_CONTENT_ID;

/**
 * Created by Cui on 2017/4/11.
 *
 * @Description 首页逻辑处理实现类
 */

public class HomePagePresenterImpl implements HomePagePresenter {
    private HomePageView mHomePageView;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;

    private static String KEY_TOP_LIST = "getHomePageTopList";

    public HomePagePresenterImpl(HomePageView homePageView, BaseActivity activity) {
        mHomePageView = homePageView;
        mHttpMethods = HttpMethods.getInstance();
        mActivity = activity;
    }

    @Override
    public void getHomePageTopList(int topList) {

        CacheSubscriber subscriber = new CacheSubscriber(KEY_TOP_LIST) {

            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);

            }
        };
        mHttpMethods.getHomePageTopList(topList, subscriber);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            final Gson gson = new Gson();
            final List<HomePageTopListBean> list = new ArrayList<>();
            JudgeIsJsonArray.judge(Goodo, "R", new JudgeIsJsonArray.OnJudged() {
                @Override
                public void judged(JSONObject jsonObject) throws JSONException {
                    HomePageTopListBean bean = gson.fromJson(jsonObject.toString(), HomePageTopListBean.class);
                    list.add(bean);
                }
            });
            mHomePageView.getTopListBeen(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startToNewsListActivity(int position) {
        Intent it = new Intent(mActivity, NewsListActivity.class);
        it.putExtra(MyConfig.KEY_POSITION, position);
        mActivity.startActivity(it);
    }

    @Override
    public void startToPicDetailActivity(String contentId) {
        Intent it = new Intent(mActivity, HomePagePicDetailActivity.class);
        it.putExtra(KEY_CONTENT_ID, contentId);
        it.putExtra(MyConfig.KEY_TITLE, MyConfig.TOP_LIST_DETAIL_TITLE);
        mActivity.startActivity(it);
    }

    @Override
    public void startToLoginActivity() {
        Intent it = new Intent(mActivity, LoginActivity.class);
        mActivity.startActivity(it);
    }


}
