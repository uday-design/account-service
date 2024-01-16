package com.osttra.accountservice.controller;

import com.osttra.accountservice.dto.AccountDTO;
import com.osttra.accountservice.exception.AccountAlreadyExistException;
import com.osttra.accountservice.exception.AccountNotFoundException;
import com.osttra.accountservice.exception.InsufficientBalanceException;
import com.osttra.accountservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void testGetAccount() throws AccountNotFoundException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        when(accountService.getAccountById(anyInt())).thenReturn(accountDTO);
        AccountDTO account = accountController.getAccount(1);
        assertEquals(1, account.getId());
    }

    @Test
    public void testGetAccountException() throws AccountNotFoundException {
        when(accountService.getAccountById(anyInt())).thenThrow(AccountNotFoundException.class);
        AccountNotFoundException thrown = assertThrows(
                AccountNotFoundException.class,
                () -> accountController.getAccount(1),
                "Expected getAccount() to throw, but it didn't"
        );
    }

    @Test
    public void testGetAllAccounts() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        List<AccountDTO> accountDTOS = new ArrayList<>();
        accountDTOS.add(accountDTO);
        when(accountService.getAllAccount()).thenReturn(accountDTOS);
        List<AccountDTO> accounts = accountController.getAllAccounts();
        assertEquals(1, accounts.size());
    }

    @Test
    public void testAddBalance() throws AccountNotFoundException, InsufficientBalanceException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        accountDTO.setBalance(100.00);
        when(accountService.addBalance(anyInt(), anyDouble())).thenReturn(accountDTO);
        AccountDTO account = accountController.addBalance(1, 20.00);
        assertEquals(100.00, account.getBalance());
    }

    @Test
    public void testAddBalanceException() throws AccountNotFoundException, InsufficientBalanceException {
        when(accountService.addBalance(anyInt(), anyDouble())).thenThrow(AccountNotFoundException.class);
        AccountNotFoundException thrown = assertThrows(
                AccountNotFoundException.class,
                () -> accountController.addBalance(1, 20.00),
                "Expected addBalance() to throw, but it didn't"
        );
    }

    @Test
    public void testAddBalanceIBException() throws AccountNotFoundException, InsufficientBalanceException {
        when(accountService.addBalance(anyInt(), anyDouble())).thenThrow(InsufficientBalanceException.class);
        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                () -> accountController.addBalance(1, 20.00),
                "Expected deductBalance() to throw, but it didn't"
        );
    }

    @Test
    public void testCreateAccount() throws AccountAlreadyExistException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(accountDTO);
        AccountDTO account = accountController.createAccount(accountDTO);
        assertEquals(1, account.getId());
    }

    @Test
    public void testCreateAccountException() throws AccountAlreadyExistException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1);
        when(accountService.createAccount(any(AccountDTO.class))).thenThrow(AccountAlreadyExistException.class);
        AccountAlreadyExistException thrown = assertThrows(
                AccountAlreadyExistException.class,
                () -> accountController.createAccount(accountDTO),
                "Expected createAccount() to throw, but it didn't"
        );
    }

}
