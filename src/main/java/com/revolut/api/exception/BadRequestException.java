package com.revolut.api.exception;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Bad request custom exception.
 */
public class BadRequestException extends Exception
{
    public BadRequestException(String message)
    {
        super(message);
    }
}
