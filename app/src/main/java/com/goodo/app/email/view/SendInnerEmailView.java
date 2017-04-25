package com.goodo.app.email.view;

import com.goodo.app.email.model.UsersBean;

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
