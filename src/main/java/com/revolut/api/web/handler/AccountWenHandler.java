package com.revolut.api.web.handler;

import com.google.gson.Gson;
import com.revolut.api.common.HttpStatus;
import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.BadRequestException;
import com.revolut.api.exception.DuplicateAccountException;
import com.revolut.api.model.Account;
import com.revolut.api.service.AccountService;
import com.revolut.api.service.impl.AccountServiceImpl;
import com.revolut.api.validators.RequestParameterValidations;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: Provide request handling for all account request.
 */
public class AccountWenHandler
{
    private final Gson gson = new Gson();

    private final AccountService accountService = AccountServiceImpl.getInstance();

    private Router router;

    public AccountWenHandler(Router router)
    {
        this.router = router;
    }

    public AccountWenHandler register()
    {
        router.post("/account/create").blockingHandler(this::create).failureHandler(this::failure);
        router.get("/account/:id").blockingHandler(this::getAccount).failureHandler(this::failure);

        return this;
    }

    private void create(RoutingContext routingContext)
    {
        long numericId;
        double numericAmount;

        try
        {
            numericId = RequestParameterValidations.validateId(routingContext.request().getParam("id"));
            numericAmount = RequestParameterValidations.validateBalanceAmount(routingContext.request().getParam("amount"));

            accountService.createAccount(numericId, numericAmount);
            routingContext.response().setStatusCode(HttpStatus.OK).setChunked(true).end();
        }
        catch (DuplicateAccountException | BadRequestException e)
        {
            routingContext.response().setStatusCode(HttpStatus.BAD_REQUEST).setChunked(true).end(e.getMessage());
        }
        catch (Exception e)
        {
            routingContext.response().setStatusCode(HttpStatus.EXPECTATION_FAILED).setChunked(true).end(e.getMessage());
        }
    }

    private void getAccount(RoutingContext routingContext)
    {
        try
        {
            long numericId = RequestParameterValidations.validateId(routingContext.request().getParam("id"));
            Account result = accountService.getAccount(numericId);
            routingContext.response().setStatusCode(HttpStatus.OK).putHeader("Content-Type", "application/json; charset=utf-8").setChunked(true).end(gson.toJson(result));
        }
        catch (AccountNotExistsException | BadRequestException e)
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
