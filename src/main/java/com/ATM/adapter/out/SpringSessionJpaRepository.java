package com.ATM.adapter.out;

import com.ATM.application.session.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringSessionJpaRepository
        extends JpaRepository<Session, UUID> {
}
