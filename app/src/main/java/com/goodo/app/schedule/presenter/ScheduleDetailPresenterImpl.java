package com.goodo.app.schedule.presenter;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.rxjava.CacheSubscriber;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.rxjava.MySubscriber;
import com.goodo.app.schedule.EditScheduleActivity;
import com.goodo.app.schedule.model.ScheduleBean;
import com.goodo.app.schedule.model.ScheduleDetailBean;
import com.goodo.app.schedule.view.ScheduleDetailView;
import com.goodo.app.util.MyConfig;
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
    private ScheduleBean mScheduleBean;
    private ScheduleDetailBean mScheduleDetailBean;

    public ScheduleDetailPresenterImpl(ScheduleDetailView scheduleDetailView, BaseActivity activity) {
        mScheduleDetailView = scheduleDetailView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void getScheduleDetail(ScheduleBean scheduleBean) {
        mScheduleBean = scheduleBean;
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

    @Override
    public void startToEditScheduleActivity() {
        if (mScheduleDetailBean != null) {
            Intent it = new Intent(mActivity, EditScheduleActivity.class);
            it.putExtra(MyConfig.KEY_SCHEDULE_DETAIL_BEAN, mScheduleDetailBean);
            mActivity.startActivityForResult(it, MyConfig.EDIT_SCHEDULE_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyConfig.EDIT_SCHEDULE_CODE) {
                mActivity.setResult(Activity.RESULT_OK);
                if (mScheduleBean != null) {
                    getScheduleDetail(mScheduleBean);
                }
            }
        }
    }

    @Override
    public void deleteSchedule() {
        if (mScheduleDetailBean != null) {
            MySubscriber subscriber = new MySubscriber() {
                @Override
                protected void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                        if (Goodo.getInt("EID") == 0) {
                            mActivity.setResult(Activity.RESULT_OK);
                            mActivity.finish();
                        } else {
                            Toast.makeText(mActivity, "删除失败！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            mHttpMethods.deleteSchedule(mScheduleDetailBean.getId(), subscriber);
        }
    }

    private void handleResponse(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject Goodo = jsonObject.getJSONObject("Goodo");
            Gson gson = new Gson();
            mScheduleDetailBean = gson.fromJson(Goodo.toString(), ScheduleDetailBean.class);
            mScheduleDetailBean.setId(mScheduleBean.getID());
            mScheduleDetailView.getScheduleDetailBean(mScheduleDetailBean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
