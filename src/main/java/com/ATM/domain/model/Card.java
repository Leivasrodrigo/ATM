package com.ATM.domain.model;

import jakarta.persistence.*;
import lombok.*;

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
  private Long id;

  private int pin;

  @Column(unique = true, nullable = false)
  private int cardNumber;

  private boolean active;

  private int attempts;

  @OneToOne(optional = false)
  private Account account;

  public boolean isBlocked() {
    return attempts >= MAX_ATTEMPTS;
  }

  public void registerFailedAttempt() {
    this.attempts++;
  }

  public void resetAttempts() {
    this.attempts = 0;
  }
}
