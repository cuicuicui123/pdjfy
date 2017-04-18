package com.goodo.pdjfy.main;

import com.goodo.pdjfy.announcement.AnnouncementFragment;
import com.goodo.pdjfy.document.DocumentFragment;
import com.goodo.pdjfy.email.EmailFragment;
import com.goodo.pdjfy.notice.NoticeFragment;
import com.goodo.pdjfy.schedule.ScheduleFragment;

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
