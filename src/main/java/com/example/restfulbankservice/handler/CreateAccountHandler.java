package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.AccountModel;
import com.example.restfulbankservice.model.ErrorMessage;
import com.example.restfulbankservice.repository.Account;
import com.example.restfulbankservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class CreateAccountHandler {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public ResponseEntity handle(AccountModel accountModel) {
        if(accountModel.getPin().toString().length() != 4) {
            return new ResponseEntity<>(Arrays.asList(new ErrorMessage("pin must be 4 digits")), HttpStatus.BAD_REQUEST);
        }

        Long lastNumber =  getLastAccountNumber();
        Account account = accountRepository.save(Account.builder()
                .name(accountModel.getName())
                .created(LocalDateTime.now())
                .balance(new BigDecimal(0))
                .number(lastNumber)
                .pin(accountModel.getPin())
                .build());
        return new ResponseEntity<>(accountMapper.map(account), HttpStatus.OK);
    }

    public Long getLastAccountNumber(){
        Account account = accountRepository.findTopByOrderByIdDesc().orElse(null);
        if (account == null) {
            return 600000000000L;
        }
        return account.getNumber() + 1;
    }
}
