package com.goodo.pdjfy.folder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.base.BaseActivity;
import com.goodo.pdjfy.folder.model.MyFolderBean;
import com.goodo.pdjfy.folder.presenter.MyFolderAdapter;
import com.goodo.pdjfy.folder.presenter.MyFolderPresenter;
import com.goodo.pdjfy.folder.presenter.MyFolderPresenterImpl;
import com.goodo.pdjfy.folder.view.MyFolderView;
import com.goodo.pdjfy.util.IntentUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cui on 2016/10/19.
 * 文件夹页面
 * 用于展示我的文件夹里面的文件
 */

public class MyFolderActivity extends BaseActivity implements MyFolderView {
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.ll_return)
    LinearLayout mReturnLl;
    @BindView(R.id.tv_return)
    TextView mReturnTv;

    private List<MyFolderBean> mList;
    private String mCurrentPath;
    private MyFolderPresenter mPresenter;
    private MyFolderAdapter mAdapter;

    private static final int REQUEST_CODE = 1;
    private static final String FOLDER_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/pdjfy";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean result = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (result) {
            mPresenter.getList(mCurrentPath);
        } else {
            Toast.makeText(this, "没有读取文件权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_my_folder);
        ButterKnife.bind(this);
    }


    @Override
    public void initData() {
        mList = new ArrayList<>();
        mAdapter = new MyFolderAdapter(mList, this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new MyOnItemClickListener());
        mPresenter = new MyFolderPresenterImpl(this);
        mCurrentPath = FOLDER_PATH;
    }

    @Override
    protected void initEvent() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            mPresenter.getList(mCurrentPath);
        }
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getList(List<MyFolderBean> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 点击事件
     * 如果点击的位置是文件夹，则继续获取下一级
     * 点击的位置是文件则调用本地Intent打开文件
     */
    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            File file = new File(mList.get(position).getFilePath());
            if (file.isDirectory()) {
                mCurrentPath = mList.get(position).getFilePath();
                mPresenter.getList(mCurrentPath);
                setReturnLlClickListener();
            } else {
                Intent it = IntentUtil.getFileIntent(file);
                startActivity(it);
            }
        }
    }

    private void setReturnLlClickListener(){
        mReturnTv.setText("返回上一级");
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPath = new File(mCurrentPath).getParent();
                mPresenter.getList(mCurrentPath);
                if (mCurrentPath.equals(FOLDER_PATH)) {
                    setFirstFolderReturnLlClickListener();
                }
            }
        });
    }

    private void setFirstFolderReturnLlClickListener(){
        mReturnTv.setText("返回");
        mReturnLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
