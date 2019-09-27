package com.revolut.api.common;

/**
 * @author : Vivek Mehta
 * Date: 27 Sep 2019
 * Details: Application Validation Error Messages Constants.
 */
public interface ErrorMessages
{
    String NO_SUCH_ACCOUNT = "Account not exists with the account id: ";

    String INSUFFICIENT_BALANCE = "Transaction can not be processed, Insufficient balance in account : ";

    String INVALID_ID = "Invalid ID: ";

    String NULL_OR_EMPTY_ID = "Invalid null or empty ID";

    String ACCOUNT_ID_ALREADY_EXISTS = "Account already exists: ";

    String NEGATIVE_TRANSFER_AMOUNT = "Transfer amount cannot be in negative value.";

    String NEGATIVE_BALANCE = "Balance cannot be in negative value.";
}
