package com.ssafy.moabang.account.adapter.out.persistence;

import com.ssafy.moabang.account.application.domain.Account.AccountId;
import com.ssafy.moabang.account.application.port.out.AccountLock;
import org.springframework.stereotype.Component;

@Component
class NoOpAccountLock implements AccountLock {

    @Override
    public void lockAccount(AccountId accountId) {
        // do nothing
    }

    @Override
    public void releaseAccount(AccountId accountId) {
        // do nothing
    }

}
