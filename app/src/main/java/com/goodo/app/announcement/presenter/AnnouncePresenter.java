package com.goodo.app.announcement.presenter;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description 公告逻辑处理接口
 */

public interface AnnouncePresenter {
    void getAnnounceList(int page, int pageSize, String keyword);
    void startToDetailActivity(String contentId);
    void startToMoreActivity();
}
