package com.ssafy.moabang.account.application.port.in;

import com.ssafy.moabang.account.adapter.in.web.response.GetAccountBalanceResponse;
import com.ssafy.moabang.account.application.domain.Account.AccountId;

public interface GetAccountBalanceQuery {

    GetAccountBalanceResponse getAccountBalance(AccountId accountId);

}
