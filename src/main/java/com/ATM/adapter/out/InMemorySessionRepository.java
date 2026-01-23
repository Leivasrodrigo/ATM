package com.ATM.adapter.out;

import com.ATM.application.session.Session;
import com.ATM.domain.port.SessionRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemorySessionRepository implements SessionRepository {
  private final Map<UUID, Session> sessions = new ConcurrentHashMap<>();

  @Override
  public Session save(Session session) {
    sessions.put(session.getId(), session);
    return session;
  }

  @Override
  public Session findById(UUID sessionId) {
    Session session = sessions.get(sessionId);
    if (session == null) {
      throw new IllegalStateException("Session not found");
    }
    return session;
  }

  @Override
  public void delete(UUID sessionId) {
    sessions.remove(sessionId);
  }
}
