package com.ATM.application.command;

import com.ATM.application.service.AccountService;
import com.ATM.application.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BalanceCommand implements AtmCommand {

  private final AccountService accountService;
  private final AuthenticationService authenticationService;

  @Override
  public boolean supports(AtmOperation operation) {
    return AtmOperation.BALANCE == operation;
  }

  @Override
  public AtmResponse execute(AtmContext context) {
    authenticationService.ensureSessionActive(context.session());

    context.session()
            .ensureAuthenticated();

    BigDecimal balance = accountService.getBalance(context.session()
            .getAccountId());

    return AtmResponse.ok("Balance: " + balance);
  }

}
