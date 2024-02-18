package com.setebit.checkbalance.controller;

import com.setebit.checkbalance.controller.response.AccountResponse;
import com.setebit.checkbalance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/transfer")
public class BalanceController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{numberAccount}")
    public ResponseEntity<Object> transferBalance(@PathVariable("numberAccount") Long numberAccount) {
        AccountResponse accountResponse = accountService.findById(numberAccount);
        return ResponseEntity.ok(accountResponse);
    }

}
