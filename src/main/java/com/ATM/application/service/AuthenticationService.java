package com.ATM.application.service;

import com.ATM.application.session.Session;
import com.ATM.domain.exception.SessionExpiredException;
import com.ATM.domain.model.Card;
import com.ATM.domain.port.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final CardService cardService;
  private final SessionRepository sessionRepository;
  private final AccountService accountService;

  public Session createSession(Long cardNumber) {
    Card card = cardService.validateCardForSession(cardNumber);

    Session session = new Session(card.getAccount()
            .getId(), card.getCardNumber());

    return sessionRepository.save(session);
  }

  public void reValidateCard(Session session, Long cardNumber) {
    this.ensureSessionActive(session);
    session.ensureOperationSelected();
    cardService.validateCardForSession(cardNumber);
    session.markAwaitingReauth();

    sessionRepository.save(session);
  }

  public void authenticateSession(Session session, int pin) {
    cardService.validatePin(session.getCardNumber(), pin);
    session.authenticate();

    sessionRepository.save(session);
  }

  public void reauthenticateSession(Session session, int pin) {
    this.ensureSessionActive(session);
    session.ensureAwaitingReauth();
    cardService.validatePin(session.getCardNumber(), pin);
    session.reauthenticate();

    sessionRepository.save(session);
  }

  public void selectWithdraw(Session session, BigDecimal amount) {
    accountService.selectWithdraw(session.getAccountId(), amount);
    session.selectWithdraw(amount);

    sessionRepository.save(session);
  }

  public void finishWithdraw(Session session) {
    session.finishWithdraw();

    sessionRepository.save(session);
  }

  public void ensureSessionActive(Session session) {
    try {
      session.ensureNotExpired();
    } catch (SessionExpiredException e) {
      session.endSession();
      sessionRepository.save(session);
      throw e;
    }
  }

  public void endSession(Session session) {
    session.endSession();

    sessionRepository.save(session);
  }

}
