package com.ssafy.moabang.account.adapter.out.persistence;

import com.ssafy.moabang.account.application.domain.Account;
import com.ssafy.moabang.account.application.domain.Account.AccountId;
import com.ssafy.moabang.account.application.domain.Activity;
import com.ssafy.moabang.account.application.port.out.LoadAccountPort;
import com.ssafy.moabang.account.application.port.out.UpdateAccountStatePort;
import com.ssafy.moabang.account.error.AccountNotFoundException;
import com.ssafy.moabang.common.annotation.PersistenceAdapter;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@PersistenceAdapter
class AccountPersistenceAdapter implements LoadAccountPort, UpdateAccountStatePort {

    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account loadAccount(AccountId accountId, LocalDateTime baselineDate) {
        AccountJpaEntity account = accountRepository.findById(accountId.value())
                .orElseThrow(AccountNotFoundException::new);

        List<ActivityJpaEntity> activities = activityRepository.findByOwnerSince(
                accountId.value(),
                baselineDate);

        Long withdrawalBalance = activityRepository.getWithdrawalBalanceUntil(accountId.value(), baselineDate)
                .orElse(0L);

        Long depositBalance = activityRepository.getDepositBalanceUntil(accountId.value(), baselineDate)
                .orElse(0L);

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance
        );
    }

    @Override
    public void updateActivities(Account account) {
        account.getActivities().stream()
                .filter(Activity::isNew)
                .map(accountMapper::mapToJpaEntity)
                .forEach(activityRepository::save);
    }

}
