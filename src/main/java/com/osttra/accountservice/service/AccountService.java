package com.osttra.accountservice.service;

import com.osttra.accountservice.dto.AccountDTO;
import com.osttra.accountservice.entity.Account;
import com.osttra.accountservice.exception.AccountAlreadyExistException;
import com.osttra.accountservice.exception.AccountNotFoundException;
import com.osttra.accountservice.exception.InsufficientBalanceException;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO account) throws AccountAlreadyExistException;

    AccountDTO getAccountById(Integer id) throws AccountNotFoundException;

    List<AccountDTO> getAllAccount();

    AccountDTO addBalance(Integer id, Double amount) throws AccountNotFoundException, InsufficientBalanceException;

}
