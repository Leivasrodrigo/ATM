package com.ATM.application.service;

import com.ATM.domain.exception.CardNotFoundException;
import com.ATM.domain.model.Card;
import com.ATM.domain.port.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  public Card validateCardForSession(int cardNumber) {

    Card card = cardRepository.findByCardNumber(cardNumber)
            .orElseThrow(() ->
                    new CardNotFoundException("Card not found"));

    if (!card.isActive()) {
      throw new CardNotFoundException("Card is inactive");
    }

    if (card.isBlocked()) {
      throw new CardNotFoundException("Card is blocked");
    }

    if (card.getAccount() == null) {
      throw new CardNotFoundException("Card is not associated with an account");
    }

    return card;
  }
}
