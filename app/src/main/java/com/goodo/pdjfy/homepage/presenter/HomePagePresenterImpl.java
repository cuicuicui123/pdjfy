package com.goodo.pdjfy.homepage.presenter;

import android.content.Intent;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.HomePageTopListBean;
import com.goodo.pdjfy.homepage.view.HomePageView;
import com.goodo.pdjfy.news.NewsListActivity;
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
 * @Description 首页逻辑处理实现类
 */

public class HomePagePresenterImpl implements HomePagePresenter {
    private HomePageView mHomePageView;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;
    public static String KEY_POSITION = "position";

    public HomePagePresenterImpl(HomePageView homePageView, BaseActivity activity) {
        mHomePageView = homePageView;
        mHttpMethods = HttpMethods.getInstance();
        mActivity = activity;
    }

    @Override
    public void getHomePageTopList(int topList) {
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
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
        };
        mHttpMethods.getHomePageTopList(topList, subscriber);
    }

    @Override
    public void startToNewsListActivity(int position) {
        Intent it = new Intent(mActivity, NewsListActivity.class);
        it.putExtra(KEY_POSITION, position);
        mActivity.startActivity(it);
    }
}
