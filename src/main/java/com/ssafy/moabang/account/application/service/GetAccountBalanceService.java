package com.ssafy.moabang.account.application.service;

import com.ssafy.moabang.account.adapter.in.web.response.GetAccountBalanceResponse;
import com.ssafy.moabang.account.application.domain.Account.AccountId;
import com.ssafy.moabang.account.application.domain.Money;
import com.ssafy.moabang.account.application.port.in.GetAccountBalanceQuery;
import com.ssafy.moabang.account.application.port.out.LoadAccountPort;
import com.ssafy.moabang.common.annotation.UseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
class GetAccountBalanceService implements GetAccountBalanceQuery {

    private final LoadAccountPort loadAccountPort;

    @Override
    public GetAccountBalanceResponse getAccountBalance(AccountId accountId) {
        Money money = loadAccountPort.loadAccount(accountId, LocalDateTime.now())
                .calculateBalance();

        return new GetAccountBalanceResponse(accountId.value(), money.amount());
    }
}
