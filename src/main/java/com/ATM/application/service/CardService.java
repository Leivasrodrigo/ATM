package com.ATM.application.service;

import com.ATM.domain.exception.CardBlockedException;
import com.ATM.domain.exception.CardInactiveException;
import com.ATM.domain.exception.CardNotFoundException;
import com.ATM.domain.exception.InvalidCardException;
import com.ATM.domain.model.Card;
import com.ATM.domain.port.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  public Card validateCardForSession(Long cardNumber) {

    Card card = cardRepository.findByCardNumber(cardNumber)
            .orElseThrow(() ->
                    new CardNotFoundException("Card not found"));

    if (!card.isActive()) {
      throw new CardInactiveException("Card is inactive");
    }

    if (card.isBlocked()) {
      throw new CardBlockedException("Card is blocked");
    }

    if (card.getAccount() == null) {
      throw new InvalidCardException("Card is not associated with an account");
    }

    return card;
  }

  public void validatePin(Long cardNumber, int pin) {

    Card card = this.findByCardNumber(cardNumber);

    if (card.getPin() != pin) {
      card.registerFailedAttempt();

      if (card.getAttempts() >= 3) {
        card.block();
      }

      cardRepository.save(card);
      throw new IllegalArgumentException("Invalid PIN");
    }

    card.resetAttempts();
    cardRepository.save(card);
  }

  public Card findByCardNumber(Long cardNumber) {
    return cardRepository.findByCardNumber(cardNumber)
            .orElseThrow(() -> new CardNotFoundException("Card not found"));
  }
}