package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.AccountDTO;
import com.example.restfulbankservice.model.TransferModel;
import com.example.restfulbankservice.repository.Account;
import com.example.restfulbankservice.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferHandlerTest {

    @InjectMocks
    private TransferHandler transferHandler;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;

    @Test
    public void testSuccessfulTransfer() {
        TransferModel transferModel = new TransferModel();
        transferModel.setFrom(4444L);
        transferModel.setTo(5555L);
        transferModel.setAmount(new BigDecimal(10));
        transferModel.setPin(2222);

        Account fromAccount = new Account();
        fromAccount.setNumber(4444L);
        fromAccount.setPin(2222);
        fromAccount.setBalance(new BigDecimal(20));

        Account toAccount = new Account();
        toAccount.setNumber(5555L);
        toAccount.setBalance(new BigDecimal(5));

        when(accountRepository.findByNumber(4444L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByNumber(5555L)).thenReturn(Optional.of(toAccount));
        when(accountMapper.map(fromAccount)).thenReturn(AccountDTO.builder().number(4444L).balance(new BigDecimal(10)).build());

        ResponseEntity responseEntity = transferHandler.handle(transferModel);

        assertEquals(new BigDecimal(10), fromAccount.getBalance());
        assertEquals(new BigDecimal(15), toAccount.getBalance());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testInsufficientFunds() {
        TransferModel transferModel = new TransferModel();
        transferModel.setFrom(4444L);
        transferModel.setTo(5555L);
        transferModel.setAmount(new BigDecimal(30));
        transferModel.setPin(2222);

        Account fromAccount = new Account();
        fromAccount.setNumber(4444L);
        fromAccount.setPin(2222);
        fromAccount.setBalance(new BigDecimal(20));

        Account toAccount = new Account();
        toAccount.setNumber(5555L);
        toAccount.setBalance(new BigDecimal(5));

        when(accountRepository.findByNumber(4444L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByNumber(5555L)).thenReturn(Optional.of(toAccount));

        ResponseEntity responseEntity = transferHandler.handle(transferModel);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
