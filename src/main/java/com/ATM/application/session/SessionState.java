package com.ATM.application.session;

public enum SessionState {
  CREATED,
  AUTHENTICATED,
  OPERATION_SELECTED,
  AWAITING_REAUTH,
  REAUTHENTICATED,
  ENDED
}
