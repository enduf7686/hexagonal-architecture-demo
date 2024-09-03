package com.ssafy.moabang.account.application.service;

import com.ssafy.moabang.account.application.domain.Account;
import com.ssafy.moabang.account.application.domain.Account.AccountId;
import com.ssafy.moabang.account.application.domain.Money;
import com.ssafy.moabang.account.application.port.in.SendMoneyCommand;
import com.ssafy.moabang.account.application.port.in.SendMoneyUseCase;
import com.ssafy.moabang.account.application.port.out.AccountLock;
import com.ssafy.moabang.account.application.port.out.LoadAccountPort;
import com.ssafy.moabang.account.application.port.out.UpdateAccountStatePort;
import com.ssafy.moabang.account.error.ThresholdExceededException;
import com.ssafy.moabang.common.annotation.UseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class SendMoneyService implements SendMoneyUseCase {

    private static final Money MAXIMUM_TRANSFER_THRESHOLD = Money.of(1_000_000L);

    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        checkThreshold(command);

        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(
                command.sourceAccountId(),
                baselineDate);

        Account targetAccount = loadAccountPort.loadAccount(
                command.targetAccountId(),
                baselineDate);

        AccountId sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        AccountId targetAccountId = targetAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.money(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if (command.money().isGreaterThan(MAXIMUM_TRANSFER_THRESHOLD)) {
            throw new ThresholdExceededException(MAXIMUM_TRANSFER_THRESHOLD, command.money());
        }
    }

}




