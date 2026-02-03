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
  @GeneratedValue
  private UUID id;

  private int pin;

  @Column(unique = true, nullable = false)
  private Long cardNumber;

  private boolean active;

  private boolean blocked;

  private int attempts;

  @OneToOne(cascade = CascadeType.ALL)
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
