package com.revolut.api.service;

import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Transaction;

public interface TransactionService
{

    /**
     * Details: Processes transaction request.
     *
     * @param transaction Transaction request data to process
     * @throws AccountNotExistsException    Thrown if sender or recipient not exist
     * @throws InsufficientBalanceException Thrown when sender not having enough balance in the account
     */
    void doTransaction(Transaction transaction) throws AccountNotExistsException, InsufficientBalanceException;
}
