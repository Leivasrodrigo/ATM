package com.ATM.application.command;

import com.ATM.application.service.AccountService;
import com.ATM.application.service.AuthenticationService;
import com.ATM.application.session.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class WithdrawCommand implements AtmCommand {
        private final AccountService accountService;
        private final AuthenticationService authenticationService;

    @Override
    public boolean supports(AtmOperation operation) {
        return AtmOperation.WITHDRAW == operation;
    }

    @Override
    public AtmResponse execute(AtmContext context) {
      Session session = context.session();
      session.ensureReauthenticated();

      BigDecimal amount = session.getPendingWithdrawAmount();
      accountService.withdraw(session.getAccountId(), amount);      authenticationService.finishWithdraw(context.session());
      return AtmResponse.ok("Withdraw successful. Amount: " + amount);
    }
}
