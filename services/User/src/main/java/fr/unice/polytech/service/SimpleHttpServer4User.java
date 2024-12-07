package fr.unice.polytech.service;
import com.sun.net.httpserver.HttpServer;

import serverUtils.ApiRegistry;

import java.io.IOException;
import java.net.InetSocketAddress;


public class SimpleHttpServer4User {
    public static void main(String[] args) {
        try {
            // Enregistrement des routes
            ApiRegistry.registerRoute("POST", "/api/members", (exchange, id) -> UserHandler.createUser(exchange));
            ApiRegistry.registerRoute("GET", "/api/members/{memberId}", (exchange, id) -> UserHandler.getUserById(exchange, Integer.parseInt(id)));

            // Lancement du serveur
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/api", ApiRegistry::handleRequest);
            server.setExecutor(null);
            System.out.println("Server started on port 8000");
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
