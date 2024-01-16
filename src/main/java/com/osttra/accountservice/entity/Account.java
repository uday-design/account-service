package com.osttra.accountservice.entity;

import com.osttra.accountservice.dto.AccountDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    private Integer id;

    private Double balance;

    public Account(AccountDTO accountDTO) {
        id = accountDTO.getId();
        balance = accountDTO.getBalance();
    }
}
