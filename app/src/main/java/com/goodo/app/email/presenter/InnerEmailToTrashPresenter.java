package com.goodo.app.email.presenter;

import com.goodo.app.email.model.SendInnerEmailBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public interface InnerEmailToTrashPresenter {
    void toTrash(List<String> attachList, SendInnerEmailBean bean);
}
