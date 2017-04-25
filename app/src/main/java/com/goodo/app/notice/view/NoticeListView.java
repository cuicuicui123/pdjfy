package com.goodo.app.notice.view;

import com.goodo.app.notice.model.NoticeListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public interface NoticeListView {
    void getNoticeList(List<NoticeListBean> list);
}
