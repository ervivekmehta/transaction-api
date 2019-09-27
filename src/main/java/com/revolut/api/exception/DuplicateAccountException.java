package com.revolut.api.exception;

import com.revolut.api.common.ErrorMessages;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: To be used when same account ID added again and
 * need to throw duplicate account custom exception
 */
public class DuplicateAccountException extends Exception
{
    public DuplicateAccountException(long accountId)
    {
        super(ErrorMessages.ACCOUNT_ID_ALREADY_EXISTS + accountId);
    }
}
