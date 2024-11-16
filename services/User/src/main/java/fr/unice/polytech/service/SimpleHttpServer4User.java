package org.example;
import com.sun.net.httpserver.HttpServer;

import serverUtils.ApiRegistry;

import java.io.IOException;
import java.net.InetSocketAddress;


public class SimpleHttpServer4User {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", exchange -> {
            try {
                ApiRegistry.handleRequest(exchange);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Register routes
        ApiRegistry.registerRoute("GET", "/api/members/{memberId}", (exchange1, id1) -> {
            try {
                UserHandler.getUserById(exchange1, id1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ApiRegistry.registerRoute("POST", "/api/members", (exchange, id) -> {
            try {
                UserHandler.createUser(exchange);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ApiRegistry.registerRoute("PUT", "/api/members/{memberId}/manager", (exchange, id) -> {
            try {
                UserHandler.setUserAsManager(exchange, id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ApiRegistry.registerRoute("GET", "/api/members/{memberId}/manager", (exchange, id) -> {
            try {
                UserHandler.isUserManager(exchange, id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ApiRegistry.registerRoute("DELETE", "/api/members/{memberId}", (exchange, id) -> {
            try {
                UserHandler.deleteUser(exchange, id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        server.setExecutor(null); // Use default executor.
        server.start();
        System.out.println("Server started on port 8000");
    }
}
