package com.goodo.app.announcement.view;

import com.goodo.app.announcement.model.AnnounceListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface AnnounceView {
    void getAnnounceList(List<AnnounceListBean> list, boolean hasNewInfo, int dbSize);
}
