package com.goodo.app.email.view;

import com.goodo.app.email.model.UnitBean;
import com.goodo.app.email.model.UnitUserBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/18.
 *
 * @Description
 */

public interface SelectPersonView {
    void getUnitInfoList(List<UnitBean> unitBeanList, List<UnitUserBean>[] userBeanLists);
}
