package com.ATM.domain.port;

import com.ATM.domain.model.Card;

import java.util.Optional;

public interface CardRepository {
  Optional<Card> findById(Long id);

  Optional<Card> findByCardNumber(Long cardNumber);

  Card save(Card card);
}
