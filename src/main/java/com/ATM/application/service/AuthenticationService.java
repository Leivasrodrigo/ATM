package com.ATM.application.service;

import com.ATM.application.session.Session;
import com.ATM.domain.exception.CardNotFoundException;
import com.ATM.domain.model.Card;
import com.ATM.domain.port.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CardService cardService;
    private final SessionRepository sessionRepository;

    public Session createSession(int cardNumber) {
        Card card = cardService.validateCardForSession(cardNumber);

      Session session = new Session(
              card.getAccount().getId(),
              card.getCardNumber()
      );

      return sessionRepository.save(session);
    }

    public void authenticateSession(Session session, int pin) {
        cardService.validatePin(session.getCardNumber(), pin);
        session.authenticate();

        sessionRepository.save(session);
    }

    public void endSession(Session session) {
      session.endSession();
      sessionRepository.save(session);
    }
}
