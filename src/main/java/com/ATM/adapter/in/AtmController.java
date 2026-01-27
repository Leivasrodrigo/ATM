package com.ATM.adapter.in;

import com.ATM.application.command.AtmContext;
import com.ATM.application.command.AtmOperation;
import com.ATM.application.command.AtmResponse;
import com.ATM.application.dispatcher.AtmCommandDispatcher;
import com.ATM.application.service.AuthenticationService;
import com.ATM.application.session.Session;
import com.ATM.domain.port.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/atm")
@RequiredArgsConstructor
public class AtmController {

  private final AuthenticationService authenticationService;
  private final SessionRepository sessionRepository;
  private final AtmCommandDispatcher dispatcher;

  // Inserção do cartão
  @PostMapping("/session/{cardNumber}")
  public UUID insertCard(@PathVariable int cardNumber) {
    Session session = authenticationService.createSession(cardNumber);
    return session.getId();
  }

  @PostMapping("/session/{sessionId}/reinsert-card")
  public void reInsertCard(@PathVariable UUID sessionId, @RequestBody int cardNumber) {
    Session session = sessionRepository.findById(sessionId);
    authenticationService.reValidateCard(session, cardNumber);
  }

  // Digitação do PIN
  @PostMapping("/session/{sessionId}/authenticate")
  public void authenticate(@PathVariable UUID sessionId, @RequestBody int pin) {
    Session session = sessionRepository.findById(sessionId);
    authenticationService.authenticateSession(session, pin);
  }

  @PostMapping("/session/{sessionId}/reauthenticate")
  public void reauthenticate(@PathVariable UUID sessionId, @RequestBody int pin) {
    Session session = sessionRepository.findById(sessionId);
    authenticationService.reauthenticateSession(session, pin);
  }

  // Consultar saldo
  @GetMapping("/session/{sessionId}/balance")
  public AtmResponse balance(@PathVariable UUID sessionId) {
    Session session = sessionRepository.findById(sessionId);

    AtmContext context = new AtmContext(session);
    return dispatcher.dispatch(AtmOperation.BALANCE, context);
  }

  @PostMapping("/session/{sessionId}/select-withdraw")
  public void selectWithdraw(@PathVariable UUID sessionId, @RequestBody BigDecimal amount) {
    Session session = sessionRepository.findById(sessionId);
    authenticationService.selectWithdraw(session, amount);
  }

  @PostMapping("/session/{sessionId}/withdraw")
  public AtmResponse withdraw(@PathVariable UUID sessionId) {
    Session session = sessionRepository.findById(sessionId);

    AtmContext context = new AtmContext(session);
    return dispatcher.dispatch(AtmOperation.WITHDRAW, context);
  }

  // Encerrar sessão
  @PostMapping("/session/{sessionId}/end")
  public void endSession(@PathVariable UUID sessionId) {
    Session session = sessionRepository.findById(sessionId);
    authenticationService.endSession(session);
  }

}
