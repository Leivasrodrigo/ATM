package com.ATM.adapter.out;

import com.ATM.domain.model.Account;
import com.ATM.domain.port.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class JpaAccountRepository implements AccountRepository {

  private final SpringAccountJpaRepository jpaRepository;

  @Override
  public Optional<Account> findById(Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public Boolean hasSufficientBalance(Long accountId, BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Invalid amount");
    }

    Account account = findById(accountId)
            .orElseThrow(() -> new IllegalStateException("Account not found"));

    return account.getBalance().compareTo(amount) >= 0;
  }

  @Override
  public void save(Account account) {
    jpaRepository.save(account);
  }
}
