package com.revolut.api.model;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Account model to store account data
 */
public class Account
{
    private final Object accountLock = new Object();

    private final long id;

    private double balance;

    public Account(long id, double balance)
    {
        this.id = id;
        this.balance = balance;
    }

    public long getId()
    {
        return id;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Object getAccountLock()
    {
        return accountLock;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || this.getClass() != o.getClass())
        {
            return false;
        }

        Account account = (Account) o;

        return (this == o) || (this.id == account.id);
    }
}
