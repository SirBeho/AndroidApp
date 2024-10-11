package com.example.vertex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);

        // Configurar el manejo de cuerpos de petición
        router.route().handler(BodyHandler.create());

        // Definir un endpoint simple
        router.get("/api/hello").handler(ctx -> {
            ctx.response()
                .putHeader("content-type", "application/json")
                .end("{\"message\":\"Hello, Vert.x API!\"}");
        });

        // Iniciar el servidor HTTP
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888, http -> {
                if (http.succeeded()) {
                    startPromise.complete();
                    System.out.println("HTTP server started on port 8888");
                } else {
                    startPromise.fail(http.cause());
                }
            });
    }
}
