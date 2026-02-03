-- liquibase formatted sql

-- changeset atm:create-account-table
CREATE TABLE account (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    balance DECIMAL(15,2) NOT NULL
);

-- changeset atm:create-card-table
CREATE TABLE card (
    id BINARY(16) PRIMARY KEY,
    pin INT NOT NULL,
    card_number BIGINT NOT NULL,
    active BOOLEAN NOT NULL,
    blocked BOOLEAN NOT NULL,
    attempts INT NOT NULL,
    account_id BIGINT NOT NULL,
    CONSTRAINT uk_card_card_number UNIQUE (card_number),
    CONSTRAINT fk_card_account FOREIGN KEY (account_id)
        REFERENCES account(id)
);

-- changeset atm:create-session-table
CREATE TABLE session (
    id BINARY(16) PRIMARY KEY,
    account_id BIGINT NOT NULL,
    card_number BIGINT NOT NULL,
    state VARCHAR(50) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    pending_withdraw_amount DECIMAL(15,2) NULL,
    CONSTRAINT fk_session_account FOREIGN KEY (account_id)
        REFERENCES account(id)
);
