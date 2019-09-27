package com.revolut.api.web.handler;

import com.revolut.api.common.HttpStatus;
import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.BadRequestException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Transaction;
import com.revolut.api.service.TransactionService;
import com.revolut.api.service.impl.TransactionServiceImpl;
import com.revolut.api.validators.RequestParameterValidations;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Provide request handling for all transaction request.
 */
public class TransactionWebHandler
{

    private final TransactionService transactionService = TransactionServiceImpl.getInstance();

    private Router router;

    public TransactionWebHandler(Router router)
    {
        this.router = router;
    }

    public TransactionWebHandler register()
    {
        router.post().path("/transfer").blockingHandler(this::transfer).failureHandler(this::failure);

        return this;
    }

    private void transfer(RoutingContext routingContext)
    {
        long senderNumericId;
        long recipientNumericId;
        double numericAmount;

        try
        {
            numericAmount = RequestParameterValidations.validateTransferAmount(routingContext.request().getParam("amount"));

            senderNumericId = RequestParameterValidations.validateId(routingContext.request().getParam("from"));
            recipientNumericId = RequestParameterValidations.validateId(routingContext.request().getParam("to"));

            Transaction transaction = new Transaction(senderNumericId, recipientNumericId, numericAmount);
            transactionService.doTransaction(transaction);

            routingContext.response().setStatusCode(HttpStatus.OK).setChunked(true).end();
        }
        catch (AccountNotExistsException | BadRequestException | InsufficientBalanceException e)
        {
            routingContext.response().setStatusCode(HttpStatus.BAD_REQUEST).setChunked(true).end(e.getMessage());
        }
        catch (Exception e)
        {
            routingContext.response().setStatusCode(HttpStatus.EXPECTATION_FAILED).setChunked(true).end(e.getMessage());
        }
    }

    private void failure(RoutingContext routingContext)
    {
        routingContext.response().setStatusCode(HttpStatus.BAD_REQUEST).setChunked(true).end(routingContext.failure().getMessage());
    }
}
