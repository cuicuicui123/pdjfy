package com.goodo.pdjfy.main;

import com.goodo.pdjfy.announcement.AnnouncementFragment;
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
            default:
                return new ScheduleFragment();
        }
    }
}
