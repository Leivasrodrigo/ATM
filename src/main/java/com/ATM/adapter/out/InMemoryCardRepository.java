package com.ATM.adapter.out;

import com.ATM.domain.model.Account;
import com.ATM.domain.model.Card;
import com.ATM.domain.port.CardRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryCardRepository implements CardRepository {
  private final Map<Integer, Card> database = new HashMap<>();

  @Override
  public Optional<Card> findById(Long id) {
    return database.values()
            .stream()
            .filter(card -> card.getId().equals(id))
            .findFirst();
  }

  @Override
  public Optional<Card> findByCardNumber(int cardNumber) {
    return Optional.ofNullable(database.get(cardNumber));
  }

  @Override
  public void save(Card card) {
    database.put(card.getCardNumber(), card);
  }
}
