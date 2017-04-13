package com.goodo.pdjfy.announcement.view;

import com.goodo.pdjfy.announcement.model.AnnounceDetailBean;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface AnnounceDetailView {
    void getAnnounceDetail(AnnounceDetailBean bean);
    void getHtmlAnnounceDetail(String html);
}
