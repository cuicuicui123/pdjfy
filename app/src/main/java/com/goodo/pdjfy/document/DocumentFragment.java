package com.goodo.pdjfy.document;

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

public class DocumentFragment extends BaseMainFragment {
    @BindView(R.id.iv_menu)
    ImageView mMenuIv;

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_document, null);
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
