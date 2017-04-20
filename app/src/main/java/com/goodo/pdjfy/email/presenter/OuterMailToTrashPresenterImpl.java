package com.goodo.pdjfy.email.presenter;

import android.widget.Toast;

import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.SendOuterEmailBean;
import com.goodo.pdjfy.rxjava.HttpMethods;
import com.goodo.pdjfy.rxjava.MySubscriber;
import com.goodo.pdjfy.util.FileUtil;
import com.goodo.pdjfy.util.MyConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public class OuterMailToTrashPresenterImpl implements OuterMailToTrashPresenter {
    private HttpMethods mHttpMethods;
    private BaseActivity mActivity;

    public OuterMailToTrashPresenterImpl(BaseActivity activity) {
        mHttpMethods = HttpMethods.getInstance();
        mActivity = activity;
    }

    @Override
    public void toTrash(List<String> list, SendOuterEmailBean bean) {
        Map<String, String> map = FileUtil.getFileBase64Map(list);
        bean.setFileNames(map.get(MyConfig.KEY_FILE_NAME));
        bean.setBase64Data(map.get(MyConfig.KEY_BASE64DATA));
        MySubscriber subscriber = new MySubscriber() {
            @Override
            protected void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject Goodo = jsonObject.getJSONObject("Goodo");
                    if (Goodo.getInt("EID") == 0) {
                        mActivity.finish();
                    } else {
                        Toast.makeText(mActivity, "存草稿失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mActivity, "存草稿失败", Toast.LENGTH_SHORT).show();
                }
            }
        };
        mHttpMethods.outerEmailToTrash(bean, subscriber);
    }

}
