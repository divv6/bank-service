package com.example.restfulbankservice.handler;

import com.example.restfulbankservice.Service.AccountMapper;
import com.example.restfulbankservice.model.AccountDTO;
import com.example.restfulbankservice.model.AccountModel;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAccountHandlerTest {

    @InjectMocks
    private CreateAccountHandler createAccountHandler;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountMapper accountMapper;

    @Test
    void testHandleWithValidInput() {
        AccountModel accountModel = new AccountModel();
        accountModel.setName("Il Da");
        accountModel.setPin(1234);

        Account account = Account.builder()
                .id(1L).name("Vin").balance(new BigDecimal(0)).number(600000000000L).balance(new BigDecimal(0)).pin(1111).build();

        when(accountRepository.findTopByOrderByIdDesc()).thenReturn(java.util.Optional.of(account));

        when(accountRepository.save(any(Account.class))).thenReturn(
                new Account(2L, "Il Da", LocalDateTime.now(), 600000000001L, new BigDecimal(0), 1234));

        AccountDTO accountDTO = AccountDTO.builder().number(600000000001L).name("Il Da").created(LocalDateTime.now()).balance(new BigDecimal(0)).build();

        when(accountMapper.map(any(Account.class))).thenReturn(accountDTO);

        ResponseEntity response = createAccountHandler.handle(accountModel);

        verify(accountRepository, times(1)).findTopByOrderByIdDesc();
        verify(accountRepository, times(1)).save(any(Account.class));
        verify(accountMapper, times(1)).map(any(Account.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(600000000001L, ((AccountDTO) response.getBody()).getNumber());
        assertEquals(new BigDecimal(0), ((AccountDTO) response.getBody()).getBalance());
    }

    @Test
    void testHandleWithInvalidPin() {
        AccountModel accountModel = new AccountModel();
        accountModel.setName("Test");
        accountModel.setPin(123);

        ResponseEntity response = createAccountHandler.handle(accountModel);

        verify(accountRepository, never()).findTopByOrderByIdDesc();
        verify(accountRepository, never()).save(any(Account.class));
        verify(accountMapper, never()).map(any(Account.class));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
