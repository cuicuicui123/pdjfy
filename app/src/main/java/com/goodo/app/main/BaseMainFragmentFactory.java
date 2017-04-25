package com.goodo.app.main;

import com.goodo.app.announcement.AnnouncementFragment;
import com.goodo.app.document.DocumentFragment;
import com.goodo.app.email.EmailFragment;
import com.goodo.app.notice.NoticeFragment;
import com.goodo.app.schedule.ScheduleFragment;

/**
 * Created by Cui on 2017/4/13.
 *
 * @Description
 */

public class BaseMainFragmentFactory {
    public BaseMainFragment createFragment(int position){
        switch (position) {
            case 0:
                return new AnnouncementFragment();
            case 1:
                return new ScheduleFragment();
            case 2:
                return new EmailFragment();
            case 3:
                return new DocumentFragment();
            case 4:
                return new NoticeFragment();
            default:
                return new ScheduleFragment();
        }
    }
}
