package com.ssafy.moabang.account.adapter.in.web;

import static com.ssafy.moabang.common.util.ApiUtils.success;

import com.ssafy.moabang.account.adapter.in.web.response.GetAccountBalanceResponse;
import com.ssafy.moabang.account.application.domain.Account.AccountId;
import com.ssafy.moabang.account.application.port.in.GetAccountBalanceQuery;
import com.ssafy.moabang.common.annotation.WebAdapter;
import com.ssafy.moabang.common.util.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetAccountBalanceController {

    private final GetAccountBalanceQuery getAccountBalanceQuery;

    @GetMapping("/accounts/{accountId}/balance")
    public ApiResult<GetAccountBalanceResponse> getAccountBalance(@PathVariable Long accountId) {
        return success(getAccountBalanceQuery.getAccountBalance(new AccountId(accountId)));
    }
}
