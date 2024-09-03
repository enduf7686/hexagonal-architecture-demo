package com.ssafy.moabang.account.application.port.out;

import com.ssafy.moabang.account.application.domain.Account.AccountId;

public interface AccountLock {

    void lockAccount(AccountId accountId);

    void releaseAccount(AccountId accountId);

}
