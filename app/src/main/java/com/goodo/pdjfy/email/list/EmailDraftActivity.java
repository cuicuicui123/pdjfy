package com.goodo.pdjfy.email.list;

import android.content.Intent;

import com.goodo.pdjfy.email.EmailDetailActivity;
import com.goodo.pdjfy.email.model.EmailListBean;
import com.goodo.pdjfy.email.presenter.AllEmailListTrashPresenterImpl;
import com.goodo.pdjfy.util.MyConfig;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailDraftActivity extends BaseEmailListActivity {
    @Override
    protected void initEmailActivity() {
        mTitleTv.setText("回收箱");
        mPresenter = new AllEmailListTrashPresenterImpl(this, this);
        mIsDel = MyConfig.DELETE;
    }

    @Override
    protected void setOnItemClickEvent(int position) {
        Intent it = new Intent(this, EmailDetailActivity.class);
        EmailListBean bean = mBeanList.get(position);
        it.putExtra(MyConfig.KEY_ID, bean.getMail_ID());
        it.putExtra(MyConfig.KEY_IS_INBOX, MyConfig.NOT_INBOX);
        it.putExtra(MyConfig.KEY_POSITION, position);
        it.putExtra(MyConfig.KEY_IS_DEL, MyConfig.DELETE);
        startActivityForResult(it, MyConfig.DETAIL_CODE);
    }
}
