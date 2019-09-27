package com.revolut.api.exception;

import com.revolut.api.common.ErrorMessages;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: To be used when need to throw account not found custom exception
 */
public class AccountNotExistsException extends Exception
{
    public AccountNotExistsException(long accountId)
    {
        super(ErrorMessages.NO_SUCH_ACCOUNT + accountId);
    }
}
