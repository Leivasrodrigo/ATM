package com.ATM.application.session;

import com.ATM.domain.exception.SessionExpiredException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static com.ATM.application.session.SessionState.AUTHENTICATED;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Session {

  @Id
  @GeneratedValue
  private UUID id;
  private final Long accountId;
  private final Long cardNumber;
  private SessionState state;
  private final Instant expiresAt;
  private BigDecimal pendingWithdrawAmount;

  public Session(Long accountId, Long cardNumber) {
    this.accountId = accountId;
    this.cardNumber = cardNumber;
    this.state = SessionState.CREATED;
    this.expiresAt = Instant.now()
            .plus(Duration.ofMinutes(10));
  }

  public boolean isExpired() {
    return Instant.now()
            .isAfter(expiresAt);
  }

  public void ensureNotExpired() {
    if (isExpired()) {
      throw new SessionExpiredException();
    }
  }

  public void authenticate() {
    if (this.state != SessionState.CREATED) {
      throw new IllegalStateException("Session already in progress");
    }
    this.state = AUTHENTICATED;
  }

  public void reauthenticate() {
    if (this.state != SessionState.AWAITING_REAUTH) {
      throw new IllegalStateException("Session not awaiting reauthentication");
    }
    this.state = SessionState.REAUTHENTICATED;
  }

  public void ensureAuthenticated() {
    if (state != SessionState.AUTHENTICATED) {
      throw new IllegalStateException("Session not authenticated");
    }
  }

  public void ensureOperationSelected() {
    if (state != SessionState.OPERATION_SELECTED) {
      throw new IllegalStateException("Session state different from Operation Selected");
    }
  }

  public void ensureAwaitingReauth() {
    if (state != SessionState.AWAITING_REAUTH) {
      throw new IllegalStateException("Not awaiting for reauthentication");
    }
  }

  public void ensureReauthenticated() {
    if (state != SessionState.REAUTHENTICATED) {
      throw new IllegalStateException("Reauthentication required");
    }
  }

  public void selectWithdraw(BigDecimal amount) {
    ensureAuthenticated();
    this.pendingWithdrawAmount = amount;
    this.state = SessionState.OPERATION_SELECTED;
  }

  public BigDecimal getPendingWithdrawAmount() {
    if (pendingWithdrawAmount == null) {
      throw new IllegalStateException("No withdraw selected");
    }
    return pendingWithdrawAmount;
  }

  public void markAwaitingReauth() {
    ensureOperationSelected();
    this.state = SessionState.AWAITING_REAUTH;
  }

  public void finishWithdraw() {
    ensureReauthenticated();
    this.pendingWithdrawAmount = null;
    this.state = AUTHENTICATED;
  }

  public void endSession() {
    this.state = SessionState.ENDED;
  }

}
