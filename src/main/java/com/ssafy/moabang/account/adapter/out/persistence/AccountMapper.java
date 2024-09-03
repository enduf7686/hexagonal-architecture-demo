package com.ssafy.moabang.account.adapter.out.persistence;

import static java.util.stream.Collectors.toCollection;

import com.ssafy.moabang.account.application.domain.Account;
import com.ssafy.moabang.account.application.domain.Account.AccountId;
import com.ssafy.moabang.account.application.domain.Activity;
import com.ssafy.moabang.account.application.domain.Activity.ActivityId;
import com.ssafy.moabang.account.application.domain.ActivityWindow;
import com.ssafy.moabang.account.application.domain.Money;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
class AccountMapper {

    Account mapToDomainEntity(AccountJpaEntity account,
                              List<ActivityJpaEntity> activities,
                              Long withdrawalBalance,
                              Long depositBalance) {
        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance)
        );

        return Account.of(
                new AccountId(account.getId()),
                baselineBalance,
                mapToActivityWindow(activities)
        );
    }

    ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = activities.stream()
                .map(this::mapToActivity)
                .collect(toCollection(ArrayList::new));

        return new ActivityWindow(mappedActivities);
    }

    ActivityJpaEntity mapToJpaEntity(Activity activity) {
        return ActivityJpaEntity.builder()
                .timestamp(activity.getTimestamp())
                .ownerAccountId(activity.getOwnerAccountId().value())
                .sourceAccountId(activity.getSourceAccountId().value())
                .targetAccountId(activity.getTargetAccountId().value())
                .amount(activity.getMoney().longValue())
                .build();
    }

    private Activity mapToActivity(ActivityJpaEntity activity) {
        return new Activity(
                new ActivityId(activity.getId()),
                new AccountId(activity.getOwnerAccountId()),
                new AccountId(activity.getSourceAccountId()),
                new AccountId(activity.getTargetAccountId()),
                activity.getTimestamp(),
                Money.of(activity.getAmount())
        );
    }

}
