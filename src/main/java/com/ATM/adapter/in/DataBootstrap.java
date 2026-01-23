package com.ATM.adapter.in;

import com.ATM.domain.model.Account;
import com.ATM.domain.model.Card;
import com.ATM.domain.port.AccountRepository;
import com.ATM.domain.port.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataBootstrap {

  private final AccountRepository accountRepository;
  private final CardRepository cardRepository;

  @Bean
  ApplicationRunner loadData() {
    return args -> {

      Account account = Account.builder()
              .balance(new BigDecimal("1000.00"))
              .build();

      accountRepository.save(account);

      Card card = Card.builder()
              .cardNumber(123456)
              .pin(1234)
              .active(true)
              .blocked(false)
              .attempts(0)
              .account(account)
              .build();

      cardRepository.save(card);
    };
  }
}
