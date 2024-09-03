package com.ssafy.moabang.account.adapter.in.web;

import static com.ssafy.moabang.common.util.ApiUtils.success;

import com.ssafy.moabang.account.adapter.in.web.request.SendMoneyRequest;
import com.ssafy.moabang.account.application.port.in.SendMoneyCommand;
import com.ssafy.moabang.account.application.port.in.SendMoneyUseCase;
import com.ssafy.moabang.common.annotation.WebAdapter;
import com.ssafy.moabang.common.util.ApiUtils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class SendMoneyController {

    private final SendMoneyUseCase sendMoneyUseCase;

    @PostMapping("/accounts/send")
    public ApiResult<Boolean> sendMoney(@RequestBody SendMoneyRequest sendMoneyRequest) {
        SendMoneyCommand command = new SendMoneyCommand(
                sendMoneyRequest.sourceAccountId(),
                sendMoneyRequest.targetAccountId(),
                sendMoneyRequest.amount()
        );

        return success(sendMoneyUseCase.sendMoney(command));
    }

}
