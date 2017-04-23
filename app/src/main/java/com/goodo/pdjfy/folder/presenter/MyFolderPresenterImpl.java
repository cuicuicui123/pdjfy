package com.goodo.pdjfy.folder.presenter;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.folder.model.MyFolderBean;
import com.goodo.pdjfy.folder.view.MyFolderView;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Cui on 2016/10/19.
 * 文件夹逻辑实现类
 */

public class MyFolderPresenterImpl implements MyFolderPresenter {
    private MyFolderView mFolderView;

    public MyFolderPresenterImpl(MyFolderView folderView) {
        mFolderView = folderView;
    }

    /**
     * 获得文件列表
     * @param path
     */
    @Override
    public void getList(String path) {
        final List<MyFolderBean> list = new ArrayList<>();
        File file = new File(path);
        File[] fileList = file.listFiles();
        if (fileList != null) {
            Observable.from(fileList)
                    .map(new Func1<File, MyFolderBean>() {
                        @Override
                        public MyFolderBean call(File file) {
                            MyFolderBean bean = new MyFolderBean();
                            bean.setFileName(file.getName());
                            bean.setFilePath(file.getPath());
                            String extension = MyConfig.getFileExtension(bean.getFileName());
                            if (file.isDirectory()) { // 如果当前文件为文件夹，设置文件夹的图标
                                bean.setIconResId(R.drawable.pic_message_folder);
                            } else if (extension != null && extension.contains("image")) {
                                bean.setPicture(true);
                            } else {
                                bean.setIconResId(MyConfig.getFilePictureByName(bean.getFileName()));
                            }
                            return bean;
                        }
                    })
                    .subscribe(new Action1<MyFolderBean>() {
                        @Override
                        public void call(MyFolderBean myFolderBean) {
                            list.add(myFolderBean);
                        }
                    });
        }
    }
}
