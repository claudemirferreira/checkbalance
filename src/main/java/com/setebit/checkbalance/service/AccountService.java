package com.setebit.checkbalance.service;

import com.setebit.checkbalance.controller.request.TransferRequest;
import com.setebit.checkbalance.model.entity.Account;
import com.setebit.checkbalance.model.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public void validateWithdrawal(Long accountNumber) {
        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        if (accountOptional.isEmpty()) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }
    }

    @Transactional
    public void transferBalance(TransferRequest transferRequest) {
        log.info("Transfer initiated.");
        Account sourceAccount = findById(transferRequest.getSourceAccountNumber());
        Account targetAccount = findById(transferRequest.getTargetAccountNumber());

        // Verificação de saldo suficiente
        if (sourceAccount.getBalance() >= transferRequest.getAmount()) {
            debit(sourceAccount , transferRequest.getAmount());
            credit(targetAccount, transferRequest.getAmount());

            // Salvando as alterações
            accountRepository.save(sourceAccount);
            accountRepository.save(targetAccount);
            log.info("Transfer completed.");
        } else {
            // Lógica para lidar com saldo insuficiente
            log.error("Transfer failed: Saldo insuficiente.");
            throw new IllegalArgumentException("Transfer failed: Saldo insuficiente.");
        }
    }

    private void debit(Account account, double valor){
        account.setBalance(account.getBalance() - valor);
    }

    private void credit(Account account, double valor){
        account.setBalance(account.getBalance() + valor);
    }

    public Account findById(Long numberAccuont){
        // Verificação se a conta existe
        Optional<Account> accountOptional = accountRepository.findById(numberAccuont);
        if (accountOptional.isEmpty() ) {
            throw new IllegalArgumentException("Conta de origem ou destino não encontrada.");
        }
        return accountOptional.get();
    }

}
