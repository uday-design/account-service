package com.osttra.accountservice.dto;

import com.osttra.accountservice.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {

    private Integer id;

    private Double balance;

    public AccountDTO(Account account) {
        id = account.getId();
        balance = account.getBalance();
    }
}
