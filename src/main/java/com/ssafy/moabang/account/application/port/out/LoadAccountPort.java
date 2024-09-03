package com.ssafy.moabang.account.application.port.out;

import com.ssafy.moabang.account.application.domain.Account;
import com.ssafy.moabang.account.application.domain.Account.AccountId;
import java.time.LocalDateTime;

public interface LoadAccountPort {

    Account loadAccount(AccountId accountId, LocalDateTime baselineDate);

}
