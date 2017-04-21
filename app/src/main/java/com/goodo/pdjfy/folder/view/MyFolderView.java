package com.goodo.pdjfy.folder.view;

import com.goodo.pdjfy.folder.model.MyFolderBean;

import java.util.List;

/**
 * Created by Cui on 2016/10/19.
 * 文件夹页面接口
 */

public interface MyFolderView {
    void getList(List<MyFolderBean> list);
}
