package com.goodo.pdjfy.folder.presenter;

import com.goodo.pdjfy.R;
import com.goodo.pdjfy.folder.model.MyFolderBean;
import com.goodo.pdjfy.folder.view.MyFolderView;
import com.goodo.pdjfy.util.MyConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        List<MyFolderBean> list = new ArrayList<>();
        File file = new File(path);
        File[] fileList = file.listFiles();
        if (fileList != null) {
            try {
                int len = fileList.length;
                for (int i = 0; i < len; i++) {
                    MyFolderBean myFolderBean = new MyFolderBean();
                    File fileItem = fileList[i];
                    myFolderBean.setFileName(fileItem.getName());
                    myFolderBean.setFilePath(fileItem.getPath());
                    String extension = MyConfig.getFileExtension(myFolderBean.getFileName());
                    if (fileItem.isDirectory()) { // 如果当前文件为文件夹，设置文件夹的图标
                        myFolderBean.setIconResId(R.drawable.pic_message_folder);
                    } else if (extension != null && extension.contains("image")) {
                        myFolderBean.setPicture(true);
                    } else {
                        myFolderBean.setIconResId(MyConfig.getFilePictureByName(myFolderBean.getFileName()));
                    }
                    list.add(myFolderBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mFolderView.getList(list);
    }
}
