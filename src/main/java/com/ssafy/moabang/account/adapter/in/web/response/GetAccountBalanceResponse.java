package com.ssafy.moabang.account.adapter.in.web.response;

import java.math.BigInteger;

public record GetAccountBalanceResponse(Long accountId, BigInteger balance) {
}
