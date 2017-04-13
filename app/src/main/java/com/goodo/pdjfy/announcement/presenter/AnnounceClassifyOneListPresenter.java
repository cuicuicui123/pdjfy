package com.goodo.pdjfy.announcement.presenter;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public interface AnnounceClassifyOneListPresenter {
    void getAnnounceClassifyOneList(String id, int page, int pageSize, String keyword);
    void startToDetailActivity(String contentId);
}
