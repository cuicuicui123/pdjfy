package com.goodo.pdjfy.email.view;

import com.goodo.pdjfy.email.model.UsersBean;

/**
 * Created by Cui on 2017/4/19.
 *
 * @Description
 */

public interface SendInnerEmailView {
    void getSelReceiverPerson(UsersBean usersBean);
    void getSelCcPerson(UsersBean usersBean);
    void getSelBccPerson(UsersBean usersBean);
    void getSelAttach(String path);
}
