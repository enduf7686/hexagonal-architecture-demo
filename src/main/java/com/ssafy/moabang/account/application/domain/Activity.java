package com.ssafy.moabang.account.application.domain;

import com.ssafy.moabang.account.application.domain.Account.AccountId;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Activity {

    private ActivityId id;
    private final AccountId ownerAccountId;
    private final AccountId sourceAccountId;
    private final AccountId targetAccountId;
    private final LocalDateTime timestamp;
    private final Money money;

    public record ActivityId(Long value) {
    }

    public boolean isNew() {
        return id == null;
    }
}
