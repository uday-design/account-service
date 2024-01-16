package com.osttra.accountservice.service;

import com.osttra.accountservice.dto.AccountDTO;
import com.osttra.accountservice.entity.Account;
import com.osttra.accountservice.exception.AccountAlreadyExistException;
import com.osttra.accountservice.exception.AccountNotFoundException;
import com.osttra.accountservice.exception.InsufficientBalanceException;
import com.osttra.accountservice.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDTO createAccount(AccountDTO account) throws AccountAlreadyExistException {
        Optional<Account> account1 = accountRepository.findById(account.getId());
        if (account1.isPresent()) {
            log.error("Account " + account1.get().getId() + " already exist!");
            throw new AccountAlreadyExistException("Account " + account1.get().getId() + " already exist!");
        }
        return new AccountDTO(accountRepository.save(new Account(account)));
    }

    @Override
    public AccountDTO getAccountById(Integer id) throws AccountNotFoundException {
        return new AccountDTO(accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account " + id + " not found!")));
    }

    @Override
    public List<AccountDTO> getAllAccount() {
        List<AccountDTO> accountDTOS = new LinkedList<>();
        accountRepository.findAll().forEach(account -> {
            accountDTOS.add(new AccountDTO(account));
        });
        return accountDTOS;
    }

    @Override
    @Transactional
    public AccountDTO addBalance(Integer id, Double amount) throws AccountNotFoundException, InsufficientBalanceException {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account " + id + " not found!"));
        if(account.getBalance() + amount  < 0) {
            log.error("Insufficient Balance");
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        account.setBalance(account.getBalance() + amount);
        return new AccountDTO(accountRepository.save(account));
    }
}
