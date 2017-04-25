package com.goodo.app.email.presenter;

import android.os.Bundle;
import android.widget.Toast;

import com.goodo.app.base.BaseActivity;
import com.goodo.app.email.view.DeleteEmailView;
import com.goodo.app.rxjava.HttpMethods;
import com.goodo.app.rxjava.MySubscriber;
import com.goodo.app.schedule.MakeSureDialogFragment;
import com.goodo.app.util.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class DeleteEmailPresenterImpl implements DeleteEmailPresenter {
    private DeleteEmailView mDeleteEmailView;
    private BaseActivity mActivity;
    private HttpMethods mHttpMethods;
    private MakeSureDialogFragment mDialogFragment;

    public DeleteEmailPresenterImpl(DeleteEmailView deleteEmailView, BaseActivity activity) {
        mDeleteEmailView = deleteEmailView;
        mActivity = activity;
        mHttpMethods = HttpMethods.getInstance();
    }

    @Override
    public void deleteEmail(final int id, final int isInBox, final int isDel, final int position) {
        mDialogFragment = new MakeSureDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MyConfig.KEY_TITLE, "删除邮件");
        bundle.putString(MyConfig.KEY_DIALOG_CONTENT, "确定删除邮件？");
        mDialogFragment.setArguments(bundle);
        mDialogFragment.show(mActivity.getSupportFragmentManager(), "dialog");
        mDialogFragment.setCancelable(false);
        mDialogFragment.setOnClickSureListener(new MakeSureDialogFragment.OnClickSureListener() {
            @Override
            public void onClickSure() {
                sendRequest(id, isInBox, isDel, position);
            }
        });


    }

    private void sendRequest(int id, int isInBox, int isDel, final int position) {
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    if (Goodo.getInt("EID") == 0) {
                        mDeleteEmailView.onDeleteEmail(position);
                    } else {
                        Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mHttpMethods.deleteEmail(id, isInBox, isDel, subscriber);
    }
}
