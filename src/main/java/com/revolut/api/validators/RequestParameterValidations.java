package com.revolut.api.validators;

import com.revolut.api.common.ErrorMessages;
import com.revolut.api.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Validation Provider to validate request parameters
 */
public class RequestParameterValidations
{
    public static long validateId(String id) throws Exception
    {
        long parsedId;

        if (!StringUtils.isNotBlank(id))
        {
            throw new BadRequestException(ErrorMessages.NULL_OR_EMPTY_ID);
        }
        else
        {
            try
            {
                parsedId = Long.parseLong(id);
            }
            catch (Exception exception)
            {
                throw new BadRequestException(ErrorMessages.INVALID_ID + id);
            }
        }
        if (parsedId < 0)
        {
            throw new BadRequestException(ErrorMessages.INVALID_ID + id);
        }
        return parsedId;
    }

    public static double validateTransferAmount(String transferAmount) throws Exception
    {
        double parsedAmount;

        if (!StringUtils.isNotBlank(transferAmount))
        {
            throw new BadRequestException("Invalid transfer amount: " + transferAmount);
        }
        else
        {
            try
            {
                parsedAmount = Double.parseDouble(transferAmount);
            }
            catch (Exception exception)
            {
                throw new BadRequestException("Invalid transfer amount: " + transferAmount);
            }
        }
        if (parsedAmount < 0)
        {
            throw new BadRequestException(ErrorMessages.NEGATIVE_TRANSFER_AMOUNT);
        }
        return parsedAmount;
    }

    public static double validateBalanceAmount(String amount) throws Exception
    {
        double partialAmount;

        if (!StringUtils.isNotBlank(amount))
        {
            return 0;
        }
        else
        {
            try
            {
                partialAmount = Double.parseDouble(amount);
            }
            catch (Exception exception)
            {
                throw new BadRequestException("Invalid balance: " + amount);
            }
        }
        if (partialAmount < 0)
        {
            throw new BadRequestException("Balance cannot be in negative value.");
        }
        return partialAmount;
    }
}
