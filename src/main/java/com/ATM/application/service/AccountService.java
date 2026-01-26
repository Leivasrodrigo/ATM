package com.ATM.application.service;

import com.ATM.domain.model.Account;
import com.ATM.domain.port.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        account.withdraw(amount);

        accountRepository.save(account);
    }

    public BigDecimal getBalance(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"))
                .getBalance();
    }

    public void selectWithdraw(Long accountId, BigDecimal amount) {
      if (!this.hasSufficientBalance(accountId, amount)) {
        throw new IllegalArgumentException("Not enough balance for this operation");
      }
    }

    public Boolean hasSufficientBalance(Long accountId, BigDecimal amount) {
      return accountRepository.hasSufficientBalance(accountId, amount);
    }
}
