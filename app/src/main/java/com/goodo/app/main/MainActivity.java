package com.goodo.app.main;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;

import com.goodo.app.R;
import com.goodo.app.announcement.AnnouncementFragment;
import com.goodo.app.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2017/4/12.
 *
 * @Description 主页面
 */

public class MainActivity extends BaseActivity {
    @BindView(R.id.btn_announcement)
    ImageButton mAnnouncementBtn;
    @BindView(R.id.btn_schedule)
    ImageButton mScheduleBtn;
    @BindView(R.id.btn_document)
    ImageButton mDocumentBtn;
    @BindView(R.id.btn_official)
    ImageButton mOfficialBtn;
    @BindView(R.id.btn_notice)
    ImageButton mNoticeBtn;

    private BaseMainFragment[] mFragments;
    private List<ImageButton> mImageButtonList;
    private int mOldPosition;

    private BaseMainFragmentFactory mFactory;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mFragments = new BaseMainFragment[5];
        mImageButtonList = new ArrayList<>();
        mImageButtonList.add(mAnnouncementBtn);
        mImageButtonList.add(mScheduleBtn);
        mImageButtonList.add(mDocumentBtn);
        mImageButtonList.add(mOfficialBtn);
        mImageButtonList.add(mNoticeBtn);
        mFactory = new BaseMainFragmentFactory();
        setDefaultFragment();
    }

    @Override
    protected void initEvent() {
        int size = mImageButtonList.size();
        for (int i = 0;i < size;i ++) {
            mImageButtonList.get(i).setOnClickListener(new OnImageBtnClickListener(i));
        }
    }

    private void setDefaultFragment(){
        BaseMainFragment fragment = new AnnouncementFragment();
        mFragments[0] = fragment;
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_all, fragment).commit();
        mImageButtonList.get(0).setSelected(true);
        mOldPosition = 0;
    }

    private void setFragment(int position){
        if (position != mOldPosition) {
            mImageButtonList.get(position).setSelected(true);
            mImageButtonList.get(mOldPosition).setSelected(false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (mFragments[position] == null) {
                mFragments[position] = mFactory.createFragment(position);
                transaction.add(R.id.fl_all, mFragments[position]);
            }
            transaction.show(mFragments[position]);
            transaction.hide(mFragments[mOldPosition]).commit();
            mOldPosition = position;
        }
    }

    class OnImageBtnClickListener implements View.OnClickListener{
        private int mPosition;

        public OnImageBtnClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View view) {
            setFragment(mPosition);
        }
    }

}
