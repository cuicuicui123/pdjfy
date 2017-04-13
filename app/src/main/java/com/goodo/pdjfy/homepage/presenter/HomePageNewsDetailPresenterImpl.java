package com.goodo.pdjfy.homepage.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.homepage.model.AttachBean;
import com.goodo.pdjfy.homepage.model.HomePageDetailBean;
import com.goodo.pdjfy.homepage.view.HomePageNewsDetailView;
import com.goodo.pdjfy.homepage.view.AttachView;
import com.goodo.pdjfy.util.JudgeIsJsonArray;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 首页新闻详情页面
 */

public class HomePageNewsDetailPresenterImpl extends BaseDetailPresenterImpl {
    private AttachView mAttachView;

    public HomePageNewsDetailPresenterImpl(HomePageNewsDetailView detailView, BaseActivity activity, AttachView attachView) {
        super(detailView, activity);
        mAttachView = attachView;
    }

    protected void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            JSONObject Record = Goodo.getJSONObject("Record");
            Gson gson = new Gson();
            HomePageDetailBean bean = gson.fromJson(Record.toString(), HomePageDetailBean.class);
            mDetailView.getNewsDetailBean(bean);
            if (!Record.isNull("Attach")) {
                getAttach(Record);
            } else {
                mAttachView.getAttach(new ArrayList<AttachBean>());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAttach(JSONObject record) throws JSONException {
        final Gson gson = new Gson();
        final List<AttachBean> list = new ArrayList<>();
        JudgeIsJsonArray.judge(record, "Attach", new JudgeIsJsonArray.OnJudged() {
            @Override
            public void judged(JSONObject jsonObject) throws JSONException {
                AttachBean bean = gson.fromJson(jsonObject.toString(), AttachBean.class);
                list.add(bean);
            }
        });
        mAttachView.getAttach(list);
    }
}
