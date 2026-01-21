package com.ATM.application.session;

import lombok.Getter;

import java.util.UUID;

import static com.ATM.application.session.SessionState.AUTHENTICATED;

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
        if (this.state != SessionState.CREATED) {
            throw new IllegalStateException("Session already in progress");
        }
        this.state = AUTHENTICATED;
    }

    public void ensureAuthenticated() {
        if (state != SessionState.AUTHENTICATED) {
            throw new IllegalStateException("Session not authenticated");
        }
    }

    public void markOperationSelected() {
        ensureAuthenticated();
        this.state = SessionState.OPERATION_SELECTED;
    }

    public void finishBalanceOperation() {
        if (state != SessionState.OPERATION_SELECTED) {
            throw new IllegalStateException("No operation in progress");
        }
        this.state = SessionState.AUTHENTICATED;
    }
}
