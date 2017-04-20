package com.goodo.pdjfy.email.list;

import android.content.Intent;

import com.goodo.pdjfy.email.EmailDetailActivity;
import com.goodo.pdjfy.email.presenter.InnerEmailListReceivePresenterImpl;
import com.goodo.pdjfy.util.MyConfig;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class InnerEmailReceiveActivity extends BaseEmailListActivity {
    private int mId;

    @Override
    protected void initEmailActivity() {
        mId = getIntent().getIntExtra(MyConfig.KEY_ID, 0);
        mTitleTv.setText("收件箱");
        mPresenter = new InnerEmailListReceivePresenterImpl(this, this, mId);
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
