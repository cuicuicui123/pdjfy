package com.goodo.pdjfy.email.list;

import com.goodo.pdjfy.email.presenter.AllEmailListDraftPresenterImpl;

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

    }
}
