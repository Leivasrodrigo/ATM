package com.ATM.domain.port;

import com.ATM.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findById(Long id);

    void save(Account account);
}
