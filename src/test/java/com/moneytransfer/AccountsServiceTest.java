package com.moneytransfer;

import com.moneytransfer.exception.DuplicateAccountIdException;
import com.moneytransfer.model.Account;
import com.moneytransfer.service.AccountsService;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertThat;


public class AccountsServiceTest extends TestService {

    private AccountsService accountsService = new AccountsService();


    @Test
    public void transferFundsConcurrently() throws Exception {
        Account account1 = new Account("Id-1", new BigDecimal(1000));

        Account account2 = new Account("Id-2", new BigDecimal(1000));

        this.accountsService.createAccount(account1);
        this.accountsService.createAccount(account2);

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                accountsService.transferFunds(account1.getAccountId(), account2.getAccountId(), "100");

            }
        });

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                accountsService.transferFunds(account1.getAccountId(), account2.getAccountId(), "300");

            }
        });

        Thread t3 = new Thread(new Runnable() {

            @Override
            public void run() {
                accountsService.transferFunds(account2.getAccountId(), account1.getAccountId(), "500");

            }
        });

        Thread b1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Account Info : " + accountsService.getAccount("Id-1"));

            }
        });

        Thread b2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Account Info : " + accountsService.getAccount("Id-2"));

            }
        });

        t1.start();
        b1.start();
        t2.start();
        t3.start();
        b2.start();

    }
}
