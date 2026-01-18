package com.ATM.application.session;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Session {

    private final UUID id = UUID.randomUUID();
    private final Long accountId;
    private boolean authenticated;

    public Session(Long accountId) {
        this.accountId = accountId;
    }

    public void authenticate() {
        this.authenticated = true;
    }
}
