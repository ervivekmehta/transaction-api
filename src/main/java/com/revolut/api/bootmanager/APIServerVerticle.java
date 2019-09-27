package com.revolut.api.bootmanager;

import com.revolut.api.common.ConfigConstants;
import com.revolut.api.common.ConfigUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * @author : Vivek Mehta
 * Date: 26 Sep 2019
 * Details: API Vertx Vertile to register router port and configurations.
 */
public class APIServerVerticle extends AbstractVerticle
{
    private static final Logger _logger = LoggerFactory.getLogger(APIServerVerticle.class);

    private static final String host = ConfigUtil.getHostName();

    private static final int port = ConfigUtil.getPort();

    //vertx http server option
    private final HttpServerOptions serverOptions = new HttpServerOptions();

    private HttpServer server;

    private RouterInitializer authRouter;

    @Override
    public void start(Future<Void> startFuture) throws Exception
    {
        try
        {
            HttpServerOptions keyStoreOptions = serverOptions
                    .setHost(host)
                    .setSsl(false)
                    .setUseAlpn(false);

            Router router = Router.router(vertx);

            router.route().handler(CorsHandler.create("*")
                    .allowedHeader("Access-Control-Allow-Method")
                    .allowedHeader("Access-Control-Allow-Origin")
                    .allowedHeader("Access-Control-Allow-Credentials")
                    .allowedHeader("Content-Type"));

            authRouter = new RouterInitializer(router).register();

            // Serve the non private static pages
            router.route().handler(StaticHandler.create().setWebRoot(ConfigConstants.WEB_ROOT_DIRECTORY));

            // Start the server & set http server listening port
            server = vertx.createHttpServer(keyStoreOptions).requestHandler(router).listen(port, res ->
            {
                if (res.succeeded())
                {
                    startFuture.complete();
                }
                else
                {
                    startFuture.fail(res.cause());
                }
            });
        }
        catch (Exception exception)
        {
            _logger.error(exception);
        }
    }

    @Override
    public void stop(Future<Void> stopFuture)
    {
        try
        {
            if (this.authRouter != null)
            {
                this.authRouter.unregister();
            }

            if (this.server != null)
            {
                this.server.close();
            }
        }
        catch (Exception exception)
        {
            _logger.error(exception);
        }
    }
}
