package com.osttra.accountservice.repository;

import com.osttra.accountservice.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository  extends CrudRepository<Account, Integer> {
}
