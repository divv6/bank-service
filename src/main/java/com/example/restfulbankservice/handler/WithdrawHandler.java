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
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WithdrawHandler {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public ResponseEntity handle(DepositModel depositModel) {
        Account account = accountRepository.findByNumber(depositModel.getNumber()).orElse(null);
        List<ErrorMessage> errors = validateData(depositModel, account);
        if(!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        account.setBalance(account.getBalance().subtract(depositModel.getAmount()));
        accountRepository.save(account);
        return new ResponseEntity<>(accountMapper.map(account), HttpStatus.OK);
    }

    private List<ErrorMessage> validateData(DepositModel depositModel, Account account) {
        List<ErrorMessage> errors = new ArrayList<>();
        if(account == null){
            errors.add(new ErrorMessage("Account with number " + depositModel.getNumber() + " not found"));
            return errors;
        }
        if(!account.getPin().equals(depositModel.getPin())) {
            errors.add(new ErrorMessage("Wrong PIN"));
        }
        if(depositModel.getAmount().compareTo(new BigDecimal(0)) <= 0) {
            errors.add(new ErrorMessage("Amount must be greater than 0"));
        }
        if(account.getBalance().compareTo(new BigDecimal(0)) <= 0) {
            errors.add(new ErrorMessage("Insufficient funds"));
        }
        return errors;
    }
}
