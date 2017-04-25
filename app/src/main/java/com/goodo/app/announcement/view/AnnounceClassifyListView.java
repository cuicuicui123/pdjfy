package com.goodo.app.announcement.view;

import com.goodo.app.announcement.model.AnnounceClassifyListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 公告分类页面接口
 */

public interface AnnounceClassifyListView {
    void getAnnounceClassifyList(List<AnnounceClassifyListBean> list);
}
