package com.ATM.application.session;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Session {

    private final UUID id = UUID.randomUUID();
    private final Long accountId;
    private final int cardNumber;
    private SessionState state;

    public Session(Long accountId, int cardNumber) {
        this.accountId = accountId;
        this.cardNumber = cardNumber;
        this.state = SessionState.CREATED;
    }

    public void authenticate() {
        this.state = SessionState.AUTHENTICATED;
    }

  public boolean validate () {
    return this.state != SessionState.ENDED && this.state != SessionState.CREATED;
  }
}
