package com.goodo.pdjfy.email.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.widget.Toast;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.SelectPersonActivity;
import com.goodo.pdjfy.email.model.EmailAttachBean;
import com.goodo.pdjfy.email.model.EmailDetailBean;
import com.goodo.pdjfy.email.model.SendInnerEmailBean;
import com.goodo.pdjfy.email.model.UnitUserBean;
import com.goodo.pdjfy.email.model.UsersBean;
import com.goodo.pdjfy.email.view.SendInnerEmailView;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.rxjava.MySubscriber;
import com.goodo.pdjfy.util.GetPathFromUri4kitkat;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public class SendInnerPresenterImpl extends BaseSendInnerEmailPresenter {

    public SendInnerPresenterImpl(SendInnerEmailView sendInnerEmailView, BaseActivity activity) {
        super(sendInnerEmailView, activity);
    }

}
