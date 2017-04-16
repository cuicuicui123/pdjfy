package com.goodo.pdjfy.schedule.presenter;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.rxjava.CacheSubscriber;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.schedule.model.ScheduleBean;
import com.goodo.pdjfy.schedule.model.ScheduleDetailBean;
import com.goodo.pdjfy.schedule.view.ScheduleDetailView;
import com.goodo.pdjfy.util.MyConfig;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cui on 2017/4/14.
 *
 * @Description
 */

public class ScheduleDetailPresenterImpl implements ScheduleDetailPresenter {
    private ScheduleDetailView mScheduleDetailView;
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;
    private String KEY_SCHEDULE_DETAIL = "getScheduleDetail";

    public ScheduleDetailPresenterImpl(ScheduleDetailView scheduleDetailView, BaseActivity activity) {
        mScheduleDetailView = scheduleDetailView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getScheduleDetail(ScheduleBean scheduleBean) {
        CacheSubscriber cacheSubscriber = new CacheSubscriber(KEY_SCHEDULE_DETAIL + scheduleBean.getID()) {
            @Override
            protected void getCache(String cacheData) {
                handleResponse(cacheData);
            }

            @Override
            protected void onResponse(String response) {
                handleResponse(response);
            }
        };
        mHttpMethods.getScheduleDetail(MyConfig.SESSION_ID, MyConfig.USER_ID, MyConfig.UNIT_ID,
                scheduleBean.getID(), scheduleBean.getType(), cacheSubscriber);
    }

    private void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            Gson gson = new Gson();
            ScheduleDetailBean bean = gson.fromJson(Goodo.toString(), ScheduleDetailBean.class);
            mScheduleDetailView.getScheduleDetailBean(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
