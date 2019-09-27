package com.revolut.api.service.impl;

import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Account;
import com.revolut.api.model.Transaction;
import com.revolut.api.service.AccountService;
import com.revolut.api.service.TransactionService;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Transaction service implementation to process transaction request
 */
public class TransactionServiceImpl implements TransactionService
{

    private static TransactionService instance;

    private final AccountService accountService = AccountServiceImpl.getInstance();

    private TransactionServiceImpl()
    {
    }

    public static TransactionService getInstance()
    {
        if (instance == null)
        {
            synchronized (TransactionServiceImpl.class)
            {
                if (instance == null)
                {
                    instance = new TransactionServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void doTransaction(Transaction transaction) throws AccountNotExistsException, InsufficientBalanceException
    {
        Account senderAccount = accountService.getAccount(transaction.getSenderId());
        Account recipientAccount = accountService.getAccount(transaction.getRecipientId());

        synchronized (senderAccount.getAccountLock())
        {
            synchronized (recipientAccount.getAccountLock())
            {
                accountService.withdraw(transaction.getSenderId(), transaction.getAmount());
                try
                {
                    accountService.deposit(transaction.getRecipientId(), transaction.getAmount());
                }
                catch (Exception e)
                {
                    accountService.deposit(transaction.getSenderId(), transaction.getAmount());
                    throw e;
                }
            }
        }
    }
}
