package com.ATM.adapter.in;

import com.ATM.application.command.AtmContext;
import com.ATM.application.command.AtmOperation;
import com.ATM.application.command.AtmResponse;
import com.ATM.application.dispatcher.AtmCommandDispatcher;
import com.ATM.application.service.AccountService;
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

import java.util.UUID;

@RestController
@RequestMapping("/atm")
@RequiredArgsConstructor
public class AtmController {
  private final AuthenticationService authenticationService;
  private final SessionRepository sessionRepository;
  private final AccountService accountService;
  private final AtmCommandDispatcher dispatcher;

  // Inserção do cartão
  @PostMapping("/session/{cardNumber}")
  public UUID insertCard(@PathVariable int cardNumber) {
    Session session = authenticationService.createSession(cardNumber);
    return session.getId();
  }

  // Digitação do PIN
  @PostMapping("/session/{sessionId}/authenticate/{pin}")
  public void authenticate(@PathVariable UUID sessionId, @PathVariable int pin) {
    Session session = sessionRepository.findById(sessionId);
    authenticationService.authenticateSession(session, pin);
  }

  // Consultar saldo
  @GetMapping("/session/{sessionId}/balance")
  public AtmResponse balance(@PathVariable UUID sessionId) {
    Session session = sessionRepository.findById(sessionId);

    AtmContext context = new AtmContext(session, null);
    return dispatcher.dispatch(AtmOperation.BALANCE, context);
  }
}
