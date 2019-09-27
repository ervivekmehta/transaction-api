package com.revolut.api.bootmanager;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: API Server Boot Manager to start Server
 */
public class ServerBootManager
{
    private static final Logger _logger = LoggerFactory.getLogger(ServerBootManager.class);

    private static final Vertx vertx = Vertx.vertx();

    // Convenience method so you can run it in your IDE
    public static void main(String[] args)
    {
        start();
    }

    public static void start()
    {
        DeploymentOptions options = new DeploymentOptions()
                .setWorker(true)
                .setInstances(5) // matches the worker pool size below
                .setWorkerPoolName("the-specific-pool")
                .setWorkerPoolSize(5);

        vertx.deployVerticle("com.revolut.api.bootmanager.APIServerVerticle", options, asyncResult ->
        {

            if (asyncResult.succeeded())
            {
                _logger.info("API server is started");
            }
            else
            {
                _logger.fatal("Could not start API server");

                _logger.error("Could not start API server", asyncResult.cause());
            }
        });
    }

    public static void stop()
    {
        _logger.info("Stopping API server");

        vertx.undeploy("com.revolut.api.bootmanager.APIServerVerticle");

        _logger.info("API server Stopped");
    }
}
