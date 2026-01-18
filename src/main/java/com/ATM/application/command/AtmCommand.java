package com.ATM.application.command;

public interface AtmCommand {

    boolean supports(AtmOperation operation);

    AtmResponse execute(AtmContext context);
}
