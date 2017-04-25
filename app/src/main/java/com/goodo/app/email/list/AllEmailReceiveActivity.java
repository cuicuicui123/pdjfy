package com.goodo.app.email.list;

import android.content.Intent;

import com.goodo.app.email.EmailDetailActivity;
import com.goodo.app.email.presenter.AllEmailListReceivePresenterImpl;
import com.goodo.app.util.MyConfig;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class AllEmailReceiveActivity extends BaseEmailListActivity{
    @Override
    protected void initEmailActivity() {
        mTitleTv.setText("收件箱");
        mPresenter = new AllEmailListReceivePresenterImpl(this, this);
        mIsInBox = 1;
    }

    @Override
    protected void setOnItemClickEvent(int position) {
        Intent it = new Intent(this, EmailDetailActivity.class);
        it.putExtra(MyConfig.KEY_ID, mBeanList.get(position).getReceive_ID());
        it.putExtra(MyConfig.KEY_IS_INBOX, MyConfig.IS_INBOX);
        it.putExtra(MyConfig.KEY_POSITION, position);
        startActivityForResult(it, MyConfig.DETAIL_CODE);
    }
}
