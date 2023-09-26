package com.example.restfulbankservice.Service;

import com.example.restfulbankservice.model.AccountDTO;
import com.example.restfulbankservice.repository.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    public AccountDTO map(Account account) {
        return AccountDTO.builder().number(account.getNumber())
                .name(account.getName())
                .created(account.getCreated())
                .balance(account.getBalance()).build();
    }
}
