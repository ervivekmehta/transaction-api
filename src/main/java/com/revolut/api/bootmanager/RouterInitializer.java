package com.revolut.api.bootmanager;

import com.revolut.api.web.handler.AccountWenHandler;
import com.revolut.api.web.handler.TransactionWebHandler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: API Vertx Vertile to register router port and configurations.
 */
class RouterInitializer
{
    private Router router;

    private AccountWenHandler accountWenHandler;

    private TransactionWebHandler transactionWebHandler;

    RouterInitializer(Router router)
    {
        this.router = router;
    }

    RouterInitializer register()
    {

        accountWenHandler = new AccountWenHandler(router).register();

        transactionWebHandler = new TransactionWebHandler(router).register();

        router.route("/").handler(routingContext ->
        {
            HttpServerResponse response = routingContext.response();
            response
                    .putHeader("content-type", "text/html")
                    .end("<h1>Welcome to Revolut Payment API Task</h1>");
        });

        return this;
    }

    void unregister()
    {
        this.router.clear();
    }
}
