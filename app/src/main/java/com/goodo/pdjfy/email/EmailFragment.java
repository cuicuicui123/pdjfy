package com.goodo.pdjfy.email;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.email.list.EmailTrashActivity;
import com.goodo.pdjfy.email.list.AllEmailReceiveActivity;
import com.goodo.pdjfy.email.list.AllEmailSendActivity;
import com.goodo.pdjfy.email.list.EmailDraftActivity;
import com.goodo.pdjfy.email.list.InnerEmailReceiveActivity;
import com.goodo.pdjfy.email.list.InnerEmailSendActivity;
import com.goodo.pdjfy.email.list.OuterEmailReceiveActivity;
import com.goodo.pdjfy.email.list.OuterEmailSendActivity;
import com.goodo.pdjfy.email.model.EmailBean;
import com.goodo.pdjfy.email.presenter.EmailPresenter;
import com.goodo.pdjfy.email.presenter.EmailPresenterImpl;
import com.goodo.pdjfy.email.view.EmailView;
import com.goodo.pdjfy.main.BaseMainFragment;
import com.goodo.pdjfy.util.MyConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class EmailFragment extends BaseMainFragment implements EmailView {
    @BindView(R.id.iv_menu)
    ImageView mMenuIv;
    @BindView(R.id.ll)
    LinearLayout mLl;
    @BindView(R.id.iv_add)
    ImageView mAddIv;

    private EmailPresenter mPresenter;
    private LayoutInflater mInflater;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_email, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mPresenter = new EmailPresenterImpl(this, this);
        mInflater = LayoutInflater.from(getContext());
    }

    @Override
    protected void initEvent() {
        mPresenter.getOuterMail();
        mMenuIv.setOnClickListener(new OnMenuClickListener());
        mAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startToSendEmailActivity();
            }
        });
    }

    @Override
    public void getMailBeanList(List<EmailBean> list) {
        mLl.removeAllViews();
        int len = list.size();
        int innerEmailSize = list.get(1).getList().size() - 1;
        for (int i = 0;i < len;i ++) {
            EmailBean emailBean = list.get(i);
            String title = emailBean.getTitle();
            View parentView = mInflater.inflate(R.layout.view_email_parent, mLl, false);
            TextView titleTv = (TextView) parentView.findViewById(R.id.tv_title);
            titleTv.setText(title);
            mLl.addView(parentView);
            LinearLayout contentLl = (LinearLayout) parentView.findViewById(R.id.ll_content);
            int contentLen = emailBean.getList().size();
            for (int j = 0;j < contentLen;j ++) {
                if (j == 1) {
                    int n = j;
                }
                EmailBean.ChildEmailBean childEmailBean = emailBean.getList().get(j);
                String content = childEmailBean.getContent();
                View childView = mInflater.inflate(R.layout.view_email_child, contentLl, false);
                TextView contentTv = (TextView) childView.findViewById(R.id.tv_kind);
                contentTv.setText(content);
                ImageView iv = (ImageView) childView.findViewById(R.id.iv);
                iv.setImageResource(childEmailBean.getPicRes());
                contentLl.addView(childView);
                childView.setOnClickListener(new OnChildClickListener(i, j, innerEmailSize, emailBean.getId(), childEmailBean.getId()));
            }
        }
    }

    class OnChildClickListener implements View.OnClickListener{
        int mEmail;
        int mPosition;
        int mInnerEmailClassifySize;
        int mEmailId;
        int mChildId;

        public OnChildClickListener(int email, int position, int innerEmailClassifySize, int emailId, int childId) {
            mEmail = email;
            mPosition = position;
            mInnerEmailClassifySize = innerEmailClassifySize;
            mEmailId = emailId;
            mChildId = childId;
        }

        @Override
        public void onClick(View v) {
            Intent it = new Intent();
            if (mEmail == 0) {
                if (mPosition == 0) {//所有邮箱收件箱
                    it.setClass(getActivity(), AllEmailReceiveActivity.class);
                } else if (mPosition == 1) {//所有邮箱发件箱
                    it.setClass(getActivity(), AllEmailSendActivity.class);
                } else if (mPosition == 2) {//所有邮箱草稿箱
                    it.setClass(getActivity(), EmailTrashActivity.class);
                } else if (mPosition == 3) {//所有邮箱回收箱
                    it.setClass(getActivity(), EmailDraftActivity.class);
                }
            } else if (mEmail == 1){
                if (mPosition < mInnerEmailClassifySize) {//内部电函收件箱
                    it.setClass(getActivity(), InnerEmailReceiveActivity.class);
                    it.putExtra(MyConfig.KEY_ID, mChildId);
                } else {//内部电函发件箱
                    it.setClass(getActivity(), InnerEmailSendActivity.class);
                }
            } else {
                if (mPosition == 0) {//外部邮箱收件箱
                    it.setClass(getActivity(), OuterEmailReceiveActivity.class);
                } else if (mPosition == 1) {//外部邮箱发件箱
                    it.setClass(getActivity(), OuterEmailSendActivity.class);
                }
                it.putExtra(MyConfig.KEY_ID, mEmailId);
            }
            startActivity(it);
        }
    }

}
