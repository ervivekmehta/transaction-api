package com.revolut.api.exception;

import com.revolut.api.common.ErrorMessages;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: To be used dureing transaction when account do not have
 * enough balance and try to widraw more then balance
 */
public class InsufficientBalanceException extends Exception
{
    public InsufficientBalanceException(long accountId)
    {
        super(ErrorMessages.INSUFFICIENT_BALANCE + accountId);
    }
}
