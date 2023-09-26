package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.AccountDTO;
import com.example.restfulbankservice.repository.Account;
import com.example.restfulbankservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class GetAccountsHandler {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public ResponseEntity handle() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDTO> accountsResponse = new ArrayList<>();
        for(Account account : accountList) {
            accountsResponse.add(accountMapper.map(account));
        }
        return new ResponseEntity<>(accountsResponse, HttpStatus.OK);
    }
}
