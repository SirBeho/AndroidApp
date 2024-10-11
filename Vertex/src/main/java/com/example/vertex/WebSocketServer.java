package com.example.vertex;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.core.http.HttpMethod;


import java.util.HashSet;
import java.util.Set;

public class WebSocketServer extends AbstractVerticle {

    @Override
    public void start() {
        // Creamos un router
        Router router = Router.router(vertx);

        // Configuramos CORS para aceptar todas las solicitudes
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("Access-Control-Allow-Methods");
        allowedHeaders.add("Access-Control-Allow-Credentials");
        allowedHeaders.add("Content-Type");

        CorsHandler corsHandler = CorsHandler.create()
            .addOrigin("*") // Specify allowed origins, or use "*" for all
            .allowedMethod(HttpMethod.GET) // Add allowed HTTP methods
            .allowedMethod(HttpMethod.POST)
            .allowedMethod(HttpMethod.PUT)
            .allowedMethod(HttpMethod.DELETE)
            .allowedHeader("Content-Type") // Add allowed headers
            .allowedHeader("Authorization");

router.route().handler(corsHandler);


        // Inicia el servidor HTTP y configura el handler para WebSockets
        HttpServer server = vertx.createHttpServer();
        
        // Configurar el WebSocketHandler
        server.webSocketHandler(this::handleWebSocket).requestHandler(router).listen(8888);
    }

    private void handleWebSocket(ServerWebSocket webSocket) {
        if ("/updates".equals(webSocket.path())) {
            webSocket.accept();
            System.out.println("Cliente [" + webSocket.textHandlerID() + "][" + webSocket.remoteAddress() + "][" + webSocket.uri() + "] conectado");

            vertx.setPeriodic(5000, id -> {
                webSocket.writeTextMessage("Actualización desde el servidor: " + System.currentTimeMillis());
            });

            webSocket.textMessageHandler(message -> {
                // Manejar mensajes entrantes del cliente
                System.out.println("Mensaje recibido del cliente: " + message);
            });
        } else {
            webSocket.reject();
        }
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new WebSocketServer());
    }
}
