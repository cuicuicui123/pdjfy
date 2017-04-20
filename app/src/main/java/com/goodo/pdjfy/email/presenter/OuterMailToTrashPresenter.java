package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.email.model.SendOuterEmailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/20.
 *
 * @Description
 */

public interface OuterMailToTrashPresenter {
    void toTrash(List<String> list, SendOuterEmailBean bean);
}
