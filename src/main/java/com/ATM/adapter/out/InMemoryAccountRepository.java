package com.ATM.adapter.out;

import com.ATM.domain.model.Account;
import com.ATM.domain.port.AccountRepository;
import org.springframework.stereotype.Repository;

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
    public void save(Account account) {
        database.put(account.getId(), account);
    }
}
