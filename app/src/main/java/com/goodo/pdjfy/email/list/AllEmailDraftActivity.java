package com.goodo.pdjfy.email.list;

import android.content.Intent;

import com.goodo.pdjfy.email.presenter.AllEmailListDraftPresenterImpl;
import com.goodo.pdjfy.email.send.DraftEmailDetailActivity;
import com.goodo.pdjfy.util.MyConfig;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class AllEmailDraftActivity extends BaseEmailListActivity {
    @Override
    protected void initEmailActivity() {
        mTitleTv.setText("草稿箱");
        mPresenter = new AllEmailListDraftPresenterImpl(this, this);
    }

    @Override
    protected void setOnItemClickEvent(int position) {
        Intent it = new Intent(AllEmailDraftActivity.this, DraftEmailDetailActivity.class);
        it.putExtra(MyConfig.KEY_ID, mBeanList.get(position).getMail_ID());
        it.putExtra(MyConfig.KEY_IS_INBOX, MyConfig.NOT_INBOX);
        startActivityForResult(it, MyConfig.DETAIL_CODE);
    }
}
