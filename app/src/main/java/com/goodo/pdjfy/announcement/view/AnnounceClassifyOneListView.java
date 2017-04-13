package com.goodo.pdjfy.announcement.view;

import com.goodo.pdjfy.announcement.model.AnnounceListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface AnnounceClassifyOneListView {
    void getAnnounceClassifyOneList(List<AnnounceListBean> list, boolean hasNewInfo, int dbSize);
}
