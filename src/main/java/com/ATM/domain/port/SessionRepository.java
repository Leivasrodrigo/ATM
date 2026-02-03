package com.ATM.domain.port;

import com.ATM.application.session.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository {
  Session save(Session session);

  Optional<Session> findById(UUID sessionId);

  void deleteById(UUID sessionId);
}
