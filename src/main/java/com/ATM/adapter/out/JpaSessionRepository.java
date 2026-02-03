package com.ATM.adapter.out;

import com.ATM.application.session.Session;
import com.ATM.domain.port.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
@RequiredArgsConstructor
public class JpaSessionRepository implements SessionRepository {

  private final SpringSessionJpaRepository jpaRepository;

  @Override
  public Session save(Session session) {
    return jpaRepository.save(session);
  }

  @Override
  public Optional<Session> findById(UUID sessionId) {
    return jpaRepository.findById(sessionId);
  }

  @Override
  public void deleteById(UUID sessionId) {
    jpaRepository.deleteById(sessionId);
  }
}
