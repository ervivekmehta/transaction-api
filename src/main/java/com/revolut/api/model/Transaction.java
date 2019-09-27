package com.revolut.api.model;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Transaction model to process transaction data
 */
public class Transaction
{
    private double amount;

    private long senderId;

    private long recipientId;

    public Transaction(long sender, long recipient, double amount)
    {
        this.amount = amount;
        this.senderId = sender;
        this.recipientId = recipient;
    }

    public double getAmount()
    {
        return amount;
    }

    public long getSenderId()
    {
        return senderId;
    }

    public long getRecipientId()
    {
        return recipientId;
    }
}
