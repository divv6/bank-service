package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.ErrorMessage;
import com.example.restfulbankservice.model.TransferModel;
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
public class TransferHandler {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public ResponseEntity handle(TransferModel depositModel) {
        Account from = accountRepository.findByNumber(depositModel.getFrom()).orElse(null);
        Account to = accountRepository.findByNumber(depositModel.getTo()).orElse(null);
        List<ErrorMessage> errors = validateData(depositModel, from, to);
        if(!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        from.setBalance(from.getBalance().subtract(depositModel.getAmount()));
        to.setBalance(to.getBalance().add(depositModel.getAmount()));
        accountRepository.save(from);
        accountRepository.save(to);
        return new ResponseEntity<>(accountMapper.map(from), HttpStatus.OK);
    }

    private List<ErrorMessage> validateData(TransferModel transferModel, Account from, Account to) {
        List<ErrorMessage> errors = new ArrayList<>();
        if(from == null){
            errors.add(new ErrorMessage("Account with number " + transferModel.getFrom() + " not found"));
            return errors;
        }
        if(to == null){
            errors.add(new ErrorMessage("Account with number " + transferModel.getTo() + " not found"));
            return errors;
        }
        if(!from.getPin().equals(transferModel.getPin())) {
            errors.add(new ErrorMessage("Wrong PIN"));
        }
        if(transferModel.getAmount().compareTo(new BigDecimal(0)) <= 0) {
            errors.add(new ErrorMessage("Amount must be greater than 0"));
        }
        if(from.getBalance().compareTo(transferModel.getAmount()) < 0) {
            errors.add(new ErrorMessage("Insufficient funds"));
        }
        return errors;
    }
}
