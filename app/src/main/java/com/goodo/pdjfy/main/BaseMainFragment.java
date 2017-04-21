package com.goodo.pdjfy.main;

import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.AppContext;
import com.goodo.pdjfy.base.BaseFragment;
import com.goodo.pdjfy.folder.MyFolderActivity;
import com.goodo.pdjfy.util.MyConfig;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 主页面父fragment，用于提供
 */

public abstract class BaseMainFragment extends BaseFragment {
    protected PopupWindow mPopupWindow;

    public class OnMenuClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            showSidePopupWindow();
        }
    }

    private void showSidePopupWindow(){
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.pop_side, null);
        int width = AppContext.getInstance().getWindowWidth() * 3 / 5;
        mPopupWindow = new PopupWindow(contentView, width, ViewGroup.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.showAtLocation(getView(), Gravity.LEFT, 0, 0);
        mPopupWindow.setBackgroundDrawable(new PaintDrawable());
        mPopupWindow.setAnimationStyle(R.style.PopSideAnim);
        mPopupWindow.update();
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        mActivity.getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPopupWindow.dismiss();
                return false;
            }
        });
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mPopupWindow.dismiss();
                return false;
            }
        });
        TextView nameTv = (TextView) contentView.findViewById(R.id.tv_name);
        nameTv.setText(MyConfig.USERNAME);
        TextView folderTv = (TextView) contentView.findViewById(R.id.tv_folder);
        TextView returnTv = (TextView) contentView.findViewById(R.id.tv_return);
        returnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        folderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getContext(), MyFolderActivity.class);
                startActivity(it);
            }
        });
    }

}
