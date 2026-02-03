package com.ATM.adapter.out;

import com.ATM.domain.model.Card;
import com.ATM.domain.port.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class JpaCardRepository implements CardRepository {

  private final SpringCardJpaRepository jpaRepository;

  @Override
  public Optional<Card> findById(Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public Optional<Card> findByCardNumber(Long cardNumber) {
    return jpaRepository.findByCardNumber(cardNumber);
  }

  @Override
  public Card save(Card card) {
    return jpaRepository.save(card);
  }
}
