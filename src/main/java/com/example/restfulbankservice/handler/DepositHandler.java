package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.DepositModel;
import com.example.restfulbankservice.model.ErrorMessage;
import com.example.restfulbankservice.repository.Account;
import com.example.restfulbankservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DepositHandler {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public ResponseEntity handle(DepositModel depositModel) {
        Account account = accountRepository.findByNumber(depositModel.getNumber()).orElse(null);
        if(account == null){
            return new ResponseEntity<>(Arrays.asList(new ErrorMessage("Account with number " + depositModel.getNumber() + " not found")), HttpStatus.BAD_REQUEST);
        }
        if(depositModel.getAmount().compareTo(new BigDecimal(0)) <= 0) {
            return new ResponseEntity<>(Arrays.asList(new ErrorMessage("Amount must be greater than 0")), HttpStatus.BAD_REQUEST);
        }

        account.setBalance(account.getBalance().add(depositModel.getAmount()));
        accountRepository.save(account);
        return new ResponseEntity<>(accountMapper.map(account), HttpStatus.OK);
    }
}
