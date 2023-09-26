package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.AccountDTO;
import com.example.restfulbankservice.model.DepositModel;
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
public class WithdrawHandlerTest {

    @InjectMocks
    private WithdrawHandler withdrawHandler;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;

    @Test
    public void testHandleValidWithdrawal() {
        DepositModel depositModel = new DepositModel(12345L, new BigDecimal(10));
        depositModel.setPin(1111);

        Account account = new Account();
        account.setNumber(12345L);
        account.setPin(1111);
        account.setBalance(new BigDecimal(30));

        when(accountRepository.findByNumber(12345L)).thenReturn(Optional.of(account));
        when(accountMapper.map(account)).thenReturn(AccountDTO.builder().number(12345L).balance(new BigDecimal(20)).build());

        ResponseEntity response = withdrawHandler.handle(depositModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testHandleInvalidWithdrawal() {
        DepositModel depositModel = new DepositModel(12345L, new BigDecimal(10));
        depositModel.setPin(1111);

        Account account = new Account();
        account.setNumber(12345L);
        account.setPin(2222);
        account.setBalance(new BigDecimal(30));

        when(accountRepository.findByNumber(12345L)).thenReturn(Optional.of(account));

        ResponseEntity response = withdrawHandler.handle(depositModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
