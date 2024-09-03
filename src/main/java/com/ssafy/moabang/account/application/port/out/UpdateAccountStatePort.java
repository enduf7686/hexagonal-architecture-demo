package com.ssafy.moabang.account.application.port.out;

import com.ssafy.moabang.account.application.domain.Account;

public interface UpdateAccountStatePort {

    void updateActivities(Account account);

}
