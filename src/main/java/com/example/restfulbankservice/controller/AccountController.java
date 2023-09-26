package com.example.restfulbankservice.controller;

import com.example.restfulbankservice.handler.CreateAccountHandler;
import com.example.restfulbankservice.handler.GetAccountsHandler;
import com.example.restfulbankservice.model.AccountModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final CreateAccountHandler createAccountHandler;
    private final GetAccountsHandler getAccountsHandler;

    @PostMapping("/api/account/create")
    public ResponseEntity createAccount(@RequestBody AccountModel accountModel) {
        return createAccountHandler.handle(accountModel);
    }

    @GetMapping("/api/account/")
    public ResponseEntity getAccounts() {
        return getAccountsHandler.handle();
    }
}
