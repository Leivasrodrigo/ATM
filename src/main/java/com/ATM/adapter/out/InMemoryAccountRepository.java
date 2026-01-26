package com.ATM.adapter.out;

import com.ATM.domain.model.Account;
import com.ATM.domain.port.AccountRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<Long, Account> database = new HashMap<>();

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Boolean hasSufficientBalance(Long accountId, BigDecimal amount) {
      if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
        throw new IllegalArgumentException("Invalid amount");
      }

      Account account = database.get(accountId);
      if (account == null) {
        throw new IllegalStateException("Account not found");
      }

      return account.getBalance().compareTo(amount) >= 0;
    }

    @Override
    public void save(Account account) {
        database.put(account.getId(), account);
    }
}
