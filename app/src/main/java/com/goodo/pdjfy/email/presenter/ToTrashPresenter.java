package com.goodo.pdjfy.email.presenter;

import com.goodo.pdjfy.email.model.SendInnerEmailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public interface ToTrashPresenter {
    void toTrash(List<String> attachList, SendInnerEmailBean bean);
}
