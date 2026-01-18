package com.ATM.application.command;

import java.math.BigDecimal;

public record AtmContext(Long accountId, BigDecimal amount) {}
