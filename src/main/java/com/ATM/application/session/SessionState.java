package com.ATM.application.session;

public enum SessionState {
  CREATED,
  AUTHENTICATED,
  AWAITING_OPERATION,
  OPERATION_SELECTED,
  AWAITING_REAUTH,
  ENDED
}
