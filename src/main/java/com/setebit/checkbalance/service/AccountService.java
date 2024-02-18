package com.setebit.checkbalance.service;

import com.setebit.checkbalance.controller.response.AccountResponse;
import com.setebit.checkbalance.exception.AccountNotFoundException;
import com.setebit.checkbalance.model.entity.Account;
import com.setebit.checkbalance.model.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public AccountResponse findById(Long accountNumber) {
        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Não encontrou a conta: " + accountNumber);
        }
        return AccountResponse
                .builder()
                .value(accountOptional.get().getBalance())
                .message("Operação realizada com sucesso")
                .numberAccount(accountOptional.get().getNumber())
                .build();
    }

}
