package com.ATM.adapter.out;

import com.ATM.domain.model.Account;
import com.ATM.domain.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringCardJpaRepository
        extends JpaRepository<Card, Long> {

  Optional<Card> findByCardNumber(Long cardNumber);
}
