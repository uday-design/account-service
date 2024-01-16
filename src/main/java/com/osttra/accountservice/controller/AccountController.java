package com.osttra.accountservice.controller;

import com.osttra.accountservice.dto.AccountDTO;
import com.osttra.accountservice.entity.Account;
import com.osttra.accountservice.exception.AccountAlreadyExistException;
import com.osttra.accountservice.exception.AccountNotFoundException;
import com.osttra.accountservice.exception.InsufficientBalanceException;
import com.osttra.accountservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping(path = "/{id}")
    public AccountDTO getAccount(@PathVariable Integer id) throws AccountNotFoundException {
        log.info("Getting account for id " + id);
        return accountService.getAccountById(id);
    }

    @GetMapping()
    public List<AccountDTO> getAllAccounts() {
        log.info("Getting all accounts");
        return accountService.getAllAccount();
    }

    @PutMapping(path = "/{id}/add/{amount}")
    public AccountDTO addBalance(@PathVariable Integer id, @PathVariable Double amount) throws AccountNotFoundException, InsufficientBalanceException {
        log.info("For account " + id + " adding amount " + amount);
        return accountService.addBalance(id, amount);
    }

    @PostMapping
    public AccountDTO createAccount(@RequestBody AccountDTO account) throws AccountAlreadyExistException {
        log.info("Creating account " + account);
        return accountService.createAccount(account);
    }

}
