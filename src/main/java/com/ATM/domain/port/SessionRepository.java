package com.ATM.domain.port;

import com.ATM.application.session.Session;

import java.util.UUID;

public interface SessionRepository {
  Session save(Session session);

  Session findById(UUID sessionId);

  void delete(UUID sessionId);
}
