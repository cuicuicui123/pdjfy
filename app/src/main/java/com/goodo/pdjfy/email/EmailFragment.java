package com.goodo.pdjfy.email;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.email.model.EmailBean;
import com.goodo.pdjfy.email.presenter.EmailPresenter;
import com.goodo.pdjfy.email.presenter.EmailPresenterImpl;
import com.goodo.pdjfy.email.view.EmailView;
import com.goodo.pdjfy.main.BaseMainFragment;

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
    }

    @Override
    public void getMailBeanList(List<EmailBean> list) {
        mLl.removeAllViews();
        int len = list.size();
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
                EmailBean.ChildEmailBean childEmailBean = emailBean.getList().get(j);
                String content = childEmailBean.getContent();
                View childView = mInflater.inflate(R.layout.view_email_child, contentLl, false);
                TextView contentTv = (TextView) childView.findViewById(R.id.tv_kind);
                contentTv.setText(content);
                ImageView iv = (ImageView) childView.findViewById(R.id.iv);
                iv.setImageResource(childEmailBean.getPicRes());
                contentLl.addView(childView);
            }
        }
    }
}
