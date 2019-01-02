package com.moneytransfer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moneytransfer.exception.NegativeAmountException;
import org.apache.log4j.Logger;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Account {
    private static Logger log = Logger.getLogger(Account.class);


    @JsonProperty(required = true)
    @NotNull
    private String accountId;

    @JsonProperty(required = false)
    private String userName;

    @JsonProperty(required = true)
    @Min(value = 0, message = "Initial balance must be positive.")
    private BigDecimal balance;

    public Account() {
    }

    public Account(String accountId) {
        this.accountId = accountId;
        this.balance = BigDecimal.ZERO;
        ;
    }

    public Account(String accountId, String userName) {
        this.accountId = accountId;
        this.userName = userName;
        this.balance = BigDecimal.ZERO;

    }

    @JsonCreator
    public Account(@JsonProperty("accountId") String accountId,
                   @JsonProperty("balance") BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public Account(String accountId, BigDecimal balance, String userName) {
        this.accountId = accountId;
        this.userName = userName;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getBalance() {
        return balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (accountId != account.accountId) return false;
        if (!userName.equals(account.userName)) return false;
        if (!balance.equals(account.balance)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = accountId.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + balance.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                '}';
    }


    public void withdraw(BigDecimal amount) {
        log.info(Thread.currentThread().getName() + " Withdrawing from : " + this.accountId + " amount : " + amount);

        if (amount.compareTo(this.balance) > 0)
            throw new NegativeAmountException("Account balance cannot be negative");
        this.balance = this.balance.subtract(amount);

        log.info(Thread.currentThread().getName() + " balance in : " + this.accountId + " balance : " + balance);
    }

    public void deposit(BigDecimal amount) {
        log.info(Thread.currentThread().getName() + " depositing into : " + this.accountId + " amount : " + amount);

        this.balance = this.balance.add(amount);

        log.info(Thread.currentThread().getName() + " balance in : " + this.accountId + " balance : " + balance);
    }

}
