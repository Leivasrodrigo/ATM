package com.ATM.application.service;

import com.ATM.application.session.Session;
import com.ATM.domain.exception.CardNotFoundException;
import com.ATM.domain.model.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CardService cardService;

    public Session createSession(int cardNumber) {
        Card card = cardService.validateCardForSession(cardNumber);

        return new Session(card.getAccount().getId(), card.getCardNumber());
    }

    public void authenticateSession(Session session, int pin) {
        cardService.validatePin(session.getCardNumber(), pin);
        session.authenticate();
    }
}
