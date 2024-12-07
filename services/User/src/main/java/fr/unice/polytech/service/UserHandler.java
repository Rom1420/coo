package fr.unice.polytech.service;

import com.sun.net.httpserver.HttpExchange;
import fr.unice.polytech.user.RegisteredUser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import serverUtils.ApiRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserHandler {
    private static Map<Integer, RegisteredUser> users = new HashMap<>();

    static {
        ApiRegistry.registerRoute("POST", "/api/members", (exchange, id) -> {
            try {
                UserHandler.createUser(exchange);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ApiRegistry.registerRoute("GET", "/api/members/{memberId}", (exchange, id) -> {
            try {
                UserHandler.getUserById(exchange, Integer.parseInt(id));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void createUser(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes());
            String[] data = requestBody.split(","); //format: "name,id,password"

            if (data.length != 3) {
                sendResponse(exchange, 400, "Invalid request format. Expected 'name,id,password'.");
                return;
            }

            String name = data[0].trim();
            int id = Integer.parseInt(data[1].trim());
            String password = data[2].trim();

            if (users.containsKey(id)) {
                sendResponse(exchange, 409, "User with this ID already exists.");
                return;
            }

            RegisteredUser user = new RegisteredUser(name, id, password);
            users.put(id, user);

            String response = "User Created: " + user.getName();
            sendResponse(exchange, 201, response);
        } catch (Exception e) {
            sendResponse(exchange, 500, "Error processing request: " + e.getMessage());
        }
    }

    public static void getUserById(HttpExchange exchange, int userId) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "Method Not Allowed");
            return;
        }

        RegisteredUser user = users.get(userId);
        if (user == null) {
            sendResponse(exchange, 404, "User not found");
            return;
        }

        String response = "User Found: " + user.getName() +
                "\nGroup Member: " + user.isGroupMember() +
                "\nRestaurant Manager: " + user.isARestaurantManager();
        sendResponse(exchange, 200, response);
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
