package com.goodo.pdjfy.email.list;

import android.content.Intent;

import com.goodo.pdjfy.email.EmailDetailActivity;
import com.goodo.pdjfy.email.presenter.OuterEmailListSendPresenterImpl;
import com.goodo.pdjfy.util.MyConfig;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class OuterEmailSendActivity extends BaseEmailListActivity {
    private int mId;

    @Override
    protected void initEmailActivity() {
        mId = getIntent().getIntExtra(MyConfig.KEY_ID, 0);
        mTitleTv.setText("发件箱");
        mPresenter = new OuterEmailListSendPresenterImpl(this, this, mId);
    }

    @Override
    protected void setOnItemClickEvent(int position) {
        Intent it = new Intent(this, EmailDetailActivity.class);
        it.putExtra(MyConfig.KEY_ID, mBeanList.get(position).getMail_ID());
        it.putExtra(MyConfig.KEY_IS_INBOX, MyConfig.NOT_INBOX);
        startActivity(it);
    }
}
