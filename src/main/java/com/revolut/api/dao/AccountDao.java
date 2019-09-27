package com.revolut.api.dao;

import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.DuplicateAccountException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Account;

public interface AccountDao
{

    /**
     * Details: Creates an account
     *
     * @param accountId AccountId for the new account
     * @param balance   Initial balance for the new account
     * @throws DuplicateAccountException Thrown when account id already exists in memory
     */
    Account createAccount(long accountId, double balance) throws DuplicateAccountException;

    /**
     * Details: To get account with given account ID
     *
     * @param accountId Requested account's ID
     * @return com.revolut.api.model.Account account object
     * @throws AccountNotExistsException Thrown when no such account exists
     */
    Account getAccount(long accountId) throws AccountNotExistsException;

    /**
     * Details: Withdraws amount from given account ID.
     *
     * @param accountId Account which to be withdraw amount
     * @param amount    Amount to be withdraw
     * @throws AccountNotExistsException    Thrown when account not exists
     * @throws InsufficientBalanceException Thrown when not enough balance amount in provided account.
     */
    void withdraw(long accountId, double amount) throws AccountNotExistsException, InsufficientBalanceException;

    /**
     * Details: Deposits money in given account ID.
     *
     * @param accountId Account in which amount will be deposited
     * @param amount    Amount needs to be deposited
     * @throws AccountNotExistsException Thrown when account not exists
     */
    void deposit(long accountId, double amount) throws AccountNotExistsException;

    /**
     * Details: Gives current balance of given account ID.
     *
     * @param accountId Requested account ID.
     * @return double | Balance of the account ID.
     * @throws AccountNotExistsException Thrown when account not exists
     */
    double getCurrentBalance(long accountId) throws AccountNotExistsException;
}
