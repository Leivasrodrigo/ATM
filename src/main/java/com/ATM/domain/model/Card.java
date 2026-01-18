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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int PIN;

    private int cardNumber;

    @OneToOne(optional = false)
    private Account accountId;
}
