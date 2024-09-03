package com.ssafy.moabang.account.application.port.in;

import static com.ssafy.moabang.common.util.ValidationUtils.validate;

import com.ssafy.moabang.account.application.domain.Account.AccountId;
import com.ssafy.moabang.account.application.domain.Money;
import jakarta.validation.constraints.NotNull;

public record SendMoneyCommand(@NotNull AccountId sourceAccountId,
                               @NotNull AccountId targetAccountId,
                               @NotNull Money money) {

    public SendMoneyCommand(Long sourceAccountId, Long targetAccountId, Long amount) {
        this(
                new AccountId(sourceAccountId),
                new AccountId(targetAccountId),
                Money.of(amount)
        );

        validate(this);
    }
}
