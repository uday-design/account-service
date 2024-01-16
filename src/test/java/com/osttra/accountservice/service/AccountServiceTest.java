package com.osttra.accountservice.service;

import com.osttra.accountservice.dto.AccountDTO;
import com.osttra.accountservice.entity.Account;
import com.osttra.accountservice.exception.AccountAlreadyExistException;
import com.osttra.accountservice.exception.AccountNotFoundException;
import com.osttra.accountservice.exception.InsufficientBalanceException;
import com.osttra.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    public void testGetAccountById() throws AccountNotFoundException {
        Account account = new Account();
        account.setId(1);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        AccountDTO accountDTO = accountService.getAccountById(1);
        assertEquals(1, accountDTO.getId());
    }

    @Test
    public void testGetAccountException() throws AccountNotFoundException {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());
        AccountNotFoundException thrown = assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccountById(1),
                "Expected getAccountById() to throw, but it didn't"
        );
    }

    @Test
    public void testGetAllAccounts() throws AccountNotFoundException {
        Account account = new Account();
        account.setId(1);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        when(accountRepository.findAll()).thenReturn(accounts);
        List<AccountDTO> accountDTOS = accountService.getAllAccount();
        assertEquals(1, accountDTOS.size());
    }

    @Test
    public void testAddBalance() throws AccountNotFoundException, InsufficientBalanceException {
        Account account = new Account();
        account.setId(1);
        account.setBalance(100.00);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDTO accountDTO = accountService.addBalance(1, 20.00);
        assertEquals(120.00, accountDTO.getBalance());
    }

    @Test
    public void testAddBalanceException() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.empty());
        AccountNotFoundException thrown = assertThrows(
                AccountNotFoundException.class,
                () -> accountService.addBalance(1, 20.00),
                "Expected addBalance() to throw, but it didn't"
        );
    }

    @Test
    public void testAddBalanceIBException() {
        Account account = new Account();
        account.setId(1);
        account.setBalance(10.00);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                () -> accountService.addBalance(1, -20.00),
                "Expected deductBalance() to throw, but it didn't"
        );
    }

    @Test
    public void testCreateAccount() throws AccountAlreadyExistException {
        Account account = new Account();
        account.setId(1);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountDTO accountDTO = accountService.createAccount(new AccountDTO());
        assertEquals(1, accountDTO.getId());
    }

    @Test
    public void testCreateAccountException() throws AccountAlreadyExistException {
        Account account = new Account();
        account.setId(1);
        account.setBalance(10.00);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account));
        AccountAlreadyExistException thrown = assertThrows(
                AccountAlreadyExistException.class,
                () -> accountService.createAccount(accountDTO),
                "Expected createAccount() to throw, but it didn't"
        );
    }
}
