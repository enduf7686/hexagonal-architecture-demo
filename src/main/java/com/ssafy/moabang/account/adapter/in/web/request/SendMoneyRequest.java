package com.ssafy.moabang.account.adapter.in.web.request;

public record SendMoneyRequest(Long sourceAccountId,
                               Long targetAccountId,
                               Long amount) {
}
