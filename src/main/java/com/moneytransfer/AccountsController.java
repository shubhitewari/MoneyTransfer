package com.moneytransfer;

import com.moneytransfer.exception.DuplicateAccountIdException;
import com.moneytransfer.model.Account;
import com.moneytransfer.model.UserTransaction;
import com.moneytransfer.service.AccountsService;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountsController {

    private final AccountsService accountsService = new AccountsService();
    private static Logger log = Logger.getLogger(AccountsController.class);


    @POST
    @Path("/create")
    public Response createAccount(Account account){
        log.info("Creating account: " + account.toString());
        try {
            this.accountsService.createAccount(account);
        } catch (DuplicateAccountIdException daie) {
            throw new WebApplicationException(daie.getMessage(), Response.Status.BAD_REQUEST);
        }
        log.info("Account: " + account.getAccountId() + " created successfully.");
        return Response.status(Response.Status.CREATED).build();
    }


    @GET
    @Path("/{accountId}")
    public Account getAccount(@PathParam("accountId") String accountId) {
        return this.accountsService.getAccount(accountId);
    }


    @POST
    @Path("/transfer")
    public Response transferFund(UserTransaction transaction) {
        if (transaction.getAmount().intValue() <= 0) {
            throw new WebApplicationException("Negative amount cannot be transferred", Response.Status.BAD_REQUEST);
        }
        accountsService.transferFunds(transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount().toString());
        return Response.status(Response.Status.OK).build();
    }


}
