package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.DepositModel;
import com.example.restfulbankservice.model.AccountDTO;
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
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepositHandlerTest {

    @InjectMocks
    private DepositHandler depositHandler;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;

    @Test
    public void testHandleValidDeposit() {
        DepositModel depositModel = new DepositModel(600000000000L, new BigDecimal(100));
        Account account = new Account();
        account.setId(1L);
        account.setBalance(new BigDecimal(0));
        account.setNumber(600000000000L);
        account.setName("test");
        account.setCreated(LocalDateTime.now());
        account.setPin(1234);
        when(accountRepository.findByNumber(600000000000L)).thenReturn(Optional.of(account));
        AccountDTO accountDTO = AccountDTO.builder().balance(new BigDecimal(100)).name("test").created(account.getCreated()).number(600000000000L).build();
        when(accountMapper.map(account)).thenReturn(accountDTO);

        ResponseEntity response = depositHandler.handle(depositModel);

        verify(accountRepository, times(1)).findByNumber(600000000000L);
        verify(accountRepository, times(1)).save(account);
        verify(accountMapper, times(1)).map(account);
        assertEquals(account.getBalance(), new BigDecimal(100));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testHandleAccountNotFound() {
        DepositModel depositModel = new DepositModel(600000000000L, new BigDecimal(100));
        when(accountRepository.findByNumber(600000000000L)).thenReturn(Optional.empty());

        ResponseEntity response = depositHandler.handle(depositModel);

        verify(accountRepository, times(1)).findByNumber(600000000000L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testHandleInvalidAmount() {
        DepositModel depositModel = new DepositModel(600000000000L, new BigDecimal(0));

        ResponseEntity response = depositHandler.handle(depositModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
