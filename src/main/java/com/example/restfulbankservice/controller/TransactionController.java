package com.example.restfulbankservice.controller;

import com.example.restfulbankservice.handler.DepositHandler;
import com.example.restfulbankservice.handler.TransferHandler;
import com.example.restfulbankservice.handler.WithdrawHandler;
import com.example.restfulbankservice.model.DepositModel;
import com.example.restfulbankservice.model.TransferModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final DepositHandler depositHandler;
    private final WithdrawHandler withdrawhandler;
    private final TransferHandler transferHandler;

    @PostMapping("/api/transaction/deposit")
    public ResponseEntity deposit(@RequestBody DepositModel depositModel) {
        return depositHandler.handle(depositModel);
    }

    @PostMapping("/api/transaction/withdraw")
    public ResponseEntity withdraw(@RequestBody DepositModel depositModel) {
        return withdrawhandler.handle(depositModel);
    }

    @PostMapping("/api/transaction/transfer")
    public ResponseEntity transfer(@RequestBody TransferModel transferModel) {
        return transferHandler.handle(transferModel);
    }
}
