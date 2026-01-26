package com.ATM.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

  public static final int MAX_ATTEMPTS = 3;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;

  private int pin;

  @Column(unique = true, nullable = false)
  private int cardNumber;

  private boolean active;

  private boolean blocked;

  private int attempts;

  @OneToOne(optional = false)
  private Account account;

    public void registerFailedAttempt() {
    this.attempts++;
  }

  public void resetAttempts() {
    this.attempts = 0;
  }

  public void block() {
    this.blocked = true;
  }
}
