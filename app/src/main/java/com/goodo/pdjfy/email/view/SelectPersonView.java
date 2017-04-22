package com.goodo.pdjfy.email.view;

import com.goodo.pdjfy.email.model.UnitBean;
import com.goodo.pdjfy.email.model.UnitUserBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public interface SelectPersonView {
    void getUnitInfoList(List<UnitBean> unitBeanList, List<UnitUserBean>[] userBeanLists);
}
