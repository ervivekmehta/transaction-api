package com.revolut.api.service.impl;


import com.revolut.api.dao.AccountDao;
import com.revolut.api.dao.impl.AccountDaoImpl;
import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.DuplicateAccountException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Account;
import com.revolut.api.service.AccountService;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Account service implementation to process account request
 */
public class AccountServiceImpl implements AccountService
{
    private static AccountService instance;

    private final AccountDao accountDao = AccountDaoImpl.getInstance();

    private AccountServiceImpl()
    {
    }

    public static AccountService getInstance()
    {
        if (instance == null)
        {
            synchronized (AccountServiceImpl.class)
            {
                if (instance == null)
                {
                    instance = new AccountServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Account createAccount(long accountId, double balance) throws DuplicateAccountException
    {
        return accountDao.createAccount(accountId, balance);
    }

    @Override
    public Account getAccount(long accountId) throws AccountNotExistsException
    {
        return accountDao.getAccount(accountId);
    }

    @Override
    public double getCurrentBalance(long accountId) throws AccountNotExistsException
    {
        return accountDao.getCurrentBalance(accountId);
    }

    @Override
    public void withdraw(long accountId, double amount) throws AccountNotExistsException, InsufficientBalanceException
    {
        accountDao.withdraw(accountId, amount);
    }

    @Override
    public void deposit(long accountId, double amount) throws AccountNotExistsException
    {
        accountDao.deposit(accountId, amount);
    }
}
