package com.setebit.checkbalance.controller;

import com.setebit.checkbalance.controller.request.TransferRequest;
import com.setebit.checkbalance.controller.response.TransferErrorResponse;
import com.setebit.checkbalance.controller.response.TransferResponse;
import com.setebit.checkbalance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    @Autowired
    private AccountService accountService;

    @PostMapping("")
    public ResponseEntity<Object> transferBalance(@RequestBody TransferRequest transferRequest) {
        try {
            accountService.validateWithdrawal(transferRequest.getSourceAccountNumber());
            accountService.transferBalance(transferRequest);
            TransferResponse transferResponse
                    = new TransferResponse("ocorreu um erro na operação ",
                    transferRequest.getAmount());
            return ResponseEntity.ok(transferResponse);
        } catch (IllegalArgumentException e) {
            TransferErrorResponse transferErrorResponse
                    = new TransferErrorResponse("ocorreu um erro na operação ");
            return ResponseEntity.internalServerError().body(transferErrorResponse);
        }
    }

}
