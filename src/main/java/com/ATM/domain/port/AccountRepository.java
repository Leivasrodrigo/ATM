package com.ATM.domain.port;

import com.ATM.domain.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(Long id);

    Boolean hasSufficientBalance(Long accountId, BigDecimal amount);

    void save(Account account);
}
