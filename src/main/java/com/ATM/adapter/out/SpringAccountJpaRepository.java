package com.ATM.adapter.out;

import com.ATM.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringAccountJpaRepository
        extends JpaRepository<Account, Long> {
}