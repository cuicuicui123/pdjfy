package com.goodo.pdjfy.notice.view;

import com.goodo.pdjfy.notice.model.NoticeListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public interface NoticeListView {
    void getNoticeList(List<NoticeListBean> list);
}
