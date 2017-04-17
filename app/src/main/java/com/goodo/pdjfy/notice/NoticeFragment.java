package com.goodo.pdjfy.notice;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.main.BaseMainFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/17.
 *
 * @Description
 */

public class NoticeFragment extends BaseMainFragment {
    @BindView(R.id.iv_menu)
    ImageView mMenuIv;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_notice, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initEvent() {
        mMenuIv.setOnClickListener(new OnMenuClickListener());
    }
}
