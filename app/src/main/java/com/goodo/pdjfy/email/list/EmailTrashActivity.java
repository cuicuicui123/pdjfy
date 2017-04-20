package com.goodo.pdjfy.email.list;

import android.app.Activity;
import android.content.Intent;

import com.goodo.pdjfy.email.model.EmailListBean;
import com.goodo.pdjfy.email.presenter.AllEmailListDraftPresenterImpl;
import com.goodo.pdjfy.email.send.TrashEmailDetailActivity;
import com.goodo.pdjfy.email.send.TrashOuterEmailDetailActivity;
import com.goodo.pdjfy.util.MyConfig;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public class EmailTrashActivity extends BaseEmailListActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == MyConfig.DETAIL_CODE) {
                int position = data.getIntExtra(MyConfig.KEY_POSITION, -1);
                if (position != -1 && position < mBeanList.size()) {
                    mBeanList.remove(position);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

    }

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
