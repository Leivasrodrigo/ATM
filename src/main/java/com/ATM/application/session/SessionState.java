package com.ATM.application.session;

public enum SessionState {
  CREATED,
  AUTHENTICATED,
  AWAITING_OPERATION,
  AMOUNT_CONFIRMED,
  REVALIDATING_CARD,
  REVALIDATING_PIN,
  ENDED
}
