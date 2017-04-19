package com.goodo.pdjfy.email.send;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.email.model.SendInnerEmailBean;
import com.goodo.pdjfy.email.model.UsersBean;
import com.goodo.pdjfy.email.presenter.SendInnerPresenter;
import com.goodo.pdjfy.email.presenter.SendInnerPresenterImpl;
import com.goodo.pdjfy.email.presenter.ToTrashPresenter;
import com.goodo.pdjfy.email.presenter.ToTrashPresenterImpl;
import com.goodo.pdjfy.email.view.SendInnerEmailView;
import com.goodo.pdjfy.email.view.ToTrashView;
import com.goodo.pdjfy.util.IntentUtil;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class SendInnerEmailActivity extends BaseSendInnerEmailActivity {
    private ToTrashPresenter mToTrashPresenter;

    @Override
    protected void handleArgument() {
        mPresenter = new SendInnerPresenterImpl(this, this);
        mToTrashTv.setVisibility(View.VISIBLE);
        mToTrashPresenter = new ToTrashPresenterImpl(this);
        mToTrashTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toTrash();
            }
        });
    }

    private void toTrash(){
        if (mTitleEdt.getText().toString() == null || mTitleEdt.getText().toString().equals("")) {
            Toast.makeText(this, "请填写标题！", Toast.LENGTH_SHORT).show();
            return;
        }
        SendInnerEmailBean bean = new SendInnerEmailBean();
        bean.setSubject(mTitleEdt.getText().toString());
        bean.setBody(mContentEdt.getText().toString());
        mPresenter.getReceivers(bean);
        mToTrashPresenter.toTrash(mPresenter.getAttachList(), bean);
    }
}
