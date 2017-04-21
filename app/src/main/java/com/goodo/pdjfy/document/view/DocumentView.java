package com.goodo.pdjfy.document.view;

import com.goodo.pdjfy.document.model.ReceiveDocumentListBean;
import com.goodo.pdjfy.document.model.SendDocumentListBean;

import java.util.List;

/**
 * Created by Cui on 2017/4/21.
 *
 * @Description
 */

public interface DocumentView {
    void getDocumentList(List<ReceiveDocumentListBean> receiveBeanList, List<SendDocumentListBean> sendBeanList);
}
