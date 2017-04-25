package com.goodo.app.schedule.presenter;

import android.app.Activity;
import android.widget.Toast;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.rxjava.MySubscriber;
import com.goodo.app.schedule.model.AddScheduleBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public abstract class BaseAddSchedulePresenterImpl implements AddSchedulePresenter {
    protected HttpMethods mHttpMethods;
    private BaseActivity mActivity;
    protected String mText;

    public BaseAddSchedulePresenterImpl(BaseActivity activity) {
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void addSchedule(AddScheduleBean bean) {
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
                        Toast.makeText(mActivity, mText, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        sendRequest(bean, subscriber);
    }

    protected abstract void sendRequest(AddScheduleBean bean, MySubscriber subscriber);
}
