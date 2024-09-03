package com.ssafy.moabang.account.error;

import com.ssafy.moabang.account.application.domain.Money;

public class ThresholdExceededException extends RuntimeException {

    public ThresholdExceededException(Money threshold, Money actual) {
        super(String.format("%s원 이하의 송금만 가능합니다! (사용자의 요청 : %s원)", threshold.amount(), actual.amount()));
    }
}
