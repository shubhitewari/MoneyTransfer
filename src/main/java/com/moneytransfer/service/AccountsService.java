package com.moneytransfer.service;

import com.moneytransfer.AccountsController;
import com.moneytransfer.exception.DuplicateAccountIdException;
import com.moneytransfer.model.Account;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AccountsService {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    private static Logger log = Logger.getLogger(AccountsController.class);

    public void createAccount(Account account) {
        Account previousAccount = accounts.putIfAbsent(account.getAccountId(), account);
        if (previousAccount != null) {
            throw new DuplicateAccountIdException(
                    "Account id " + account.getAccountId() + " already exists!");
        }

    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public void transferFunds(String accountFrom, String accountTo, String amount) {

        log.info(Thread.currentThread().getName() + " Transferring funds from : " + accountFrom + " to : " + accountTo + " amount : " + amount);

        Account fromAccount = getAccount(accountFrom);
        Account toAccount = getAccount(accountTo);

        Object firstLock, secondLock;
        if (fromAccount.getAccountId().compareTo(toAccount.getAccountId()) < 0) {
            firstLock = fromAccount;
            secondLock = toAccount;
        } else {
            firstLock = toAccount;
            secondLock = fromAccount;
        }

        synchronized (firstLock) {
            synchronized (secondLock) {
                fromAccount.withdraw(new BigDecimal(amount));
                toAccount.deposit(new BigDecimal(amount));
            }
        }
        log.info(Thread.currentThread().getName() + "  Fund Transfer successful from " + accountFrom + " to " + accountTo + " amount " + amount);
    }

}
