package com.revolut.api.dao.impl;

import com.revolut.api.dao.AccountDao;
import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.DuplicateAccountException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Account;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Manage Account Implementation.
 */
public class AccountDaoImpl implements AccountDao
{
    private static final Logger _logger = LoggerFactory.getLogger(AccountDaoImpl.class);

    private static final Object lock = new Object();

    private static AccountDao instance;

    private final ConcurrentHashMap<Long, Account> m_accounts = new ConcurrentHashMap<>();

    private AccountDaoImpl()
    {
    }

    public static AccountDao getInstance()
    {
        if (instance == null)
        {
            synchronized (AccountDaoImpl.class)
            {
                if (instance == null)
                {
                    instance = new AccountDaoImpl();
                }
            }
        }

        return instance;
    }

    @Override
    public Account createAccount(long accountId, double balance) throws DuplicateAccountException
    {
        if (m_accounts.containsKey(accountId))
        {
            throw new DuplicateAccountException(accountId);
        }
        else
        {
            synchronized (lock)
            {
                if (m_accounts.containsKey(accountId))
                {
                    throw new DuplicateAccountException(accountId);
                }
                else
                {
                    Account account = new Account(accountId, balance);
                    m_accounts.putIfAbsent(accountId, account);

                    _logger.debug("account has been created with id : " + accountId + " and balance : " + balance);

                    return account;
                }
            }
        }
    }

    @Override
    public Account getAccount(long accountId) throws AccountNotExistsException
    {
        if (m_accounts.containsKey(accountId))
        {
            return m_accounts.get(accountId);
        }
        else
        {
            throw new AccountNotExistsException(accountId);
        }
    }

    @Override
    public void withdraw(long accountId, double amount) throws AccountNotExistsException, InsufficientBalanceException
    {
        if (!m_accounts.containsKey(accountId))
        {
            throw new AccountNotExistsException(accountId);
        }
        else if (amount > m_accounts.get(accountId).getBalance())
        {
            throw new InsufficientBalanceException(accountId);
        }
        else
        {
            if (amount > m_accounts.get(accountId).getBalance())
            {
                throw new InsufficientBalanceException(accountId);
            }
            else
            {
                Account account = m_accounts.get(accountId);
                account.setBalance(account.getBalance() - amount);

                m_accounts.put(accountId, account);

                _logger.debug("account has been withdraw with id : " + accountId + " and amount : " + amount);
            }
        }
    }

    @Override
    public void deposit(long accountId, double amount) throws AccountNotExistsException
    {
        if (!m_accounts.containsKey(accountId))
        {
            throw new AccountNotExistsException(accountId);
        }
        else
        {
            Account account = m_accounts.get(accountId);
            account.setBalance(account.getBalance() + amount);

            m_accounts.put(accountId, account);

            _logger.debug("account has been deposited with id : " + accountId + " and amount : " + amount);
        }
    }

    @Override
    public double getCurrentBalance(long accountId) throws AccountNotExistsException
    {
        if (!m_accounts.containsKey(accountId))
        {
            throw new AccountNotExistsException(accountId);
        }
        else
        {
            return m_accounts.get(accountId).getBalance();
        }
    }
}
