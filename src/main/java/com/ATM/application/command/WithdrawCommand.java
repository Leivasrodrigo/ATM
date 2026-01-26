package com.ATM.application.command;

import com.ATM.application.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WithdrawCommand implements AtmCommand {
        private final AccountService accountService;

    @Override
    public boolean supports(AtmOperation operation) {
        return AtmOperation.WITHDRAW == operation;
    }

    @Override
    public AtmResponse execute(AtmContext context) {
        accountService.withdraw(context.session().getAccountId(), context.amount());
        return AtmResponse.ok("Withdraw successful");
    }
}
