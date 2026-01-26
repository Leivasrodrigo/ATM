package com.ATM.application.command;

import com.ATM.application.session.Session;

import java.math.BigDecimal;

public record AtmContext(Session session) {}
