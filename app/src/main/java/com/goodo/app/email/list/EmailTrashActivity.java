package com.goodo.app.email.list;

import android.content.Intent;

import com.goodo.app.email.model.EmailListBean;
import com.goodo.app.email.presenter.AllEmailListDraftPresenterImpl;
import com.goodo.app.email.send.TrashEmailDetailActivity;
import com.goodo.app.email.send.TrashOuterEmailDetailActivity;
import com.goodo.app.util.MyConfig;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailTrashActivity extends BaseEmailListActivity {



    @Override
    protected void initEmailActivity() {
        mTitleTv.setText("草稿箱");
        mPresenter = new AllEmailListDraftPresenterImpl(this, this);
    }

    @Override
    protected void setOnItemClickEvent(int position) {
        EmailListBean bean = mBeanList.get(position);
        Intent it = new Intent();
        if (bean.getMail_ID() > 0) {
            it.setClass(EmailTrashActivity.this, TrashEmailDetailActivity.class);
        } else {
            it.setClass(EmailTrashActivity.this, TrashOuterEmailDetailActivity.class);
            it.putExtra(MyConfig.KEY_OUTER_MAIL_ID, bean.getOuterMailAddr_ID());
        }
        it.putExtra(MyConfig.KEY_ID, mBeanList.get(position).getMail_ID());
        it.putExtra(MyConfig.KEY_IS_INBOX, MyConfig.NOT_INBOX);
        it.putExtra(MyConfig.KEY_POSITION, position);
        startActivityForResult(it, MyConfig.DETAIL_CODE);
    }
}
