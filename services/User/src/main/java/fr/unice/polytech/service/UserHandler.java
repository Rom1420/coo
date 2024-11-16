package org.example;

import com.sun.net.httpserver.HttpExchange;
import fr.unice.polytech.user.RegisteredUser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class UserHandler {

    private static final Map<Integer, RegisteredUser> users = new HashMap<>();
    public static void getUserById(HttpExchange exchange, String id) throws IOException {
        int userId = Integer.parseInt(id);
        RegisteredUser user = users.get(userId);

        if (user != null) {
            String response = "User Found: " + user.getName() +
                    "\nGroup Member: " + user.isGroupMember() +
                    "\nRestaurant Manager: " + user.isARestaurantManager();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            String response = "User Not Found";
            exchange.sendResponseHeaders(404, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public static void createUser(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        String[] data = requestBody.split(","); // Exemple: "name,id,password"
        String name = data[0].trim();
        int id = Integer.parseInt(data[1].trim());
        String password = data[2].trim();

        RegisteredUser user = new RegisteredUser(name, id, password);
        users.put(id, user);

        String response = "User Created: " + user.getName();
        exchange.sendResponseHeaders(201, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public static void setUserAsManager(HttpExchange exchange, String id) throws IOException {
        int userId = Integer.parseInt(id);
        RegisteredUser user = users.get(userId);

        if (user != null) {
            user.setRestaurantManager();
            String response = "User " + user.getName() + " is now a Restaurant Manager.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            String response = "User Not Found";
            exchange.sendResponseHeaders(404, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public static void isUserManager(HttpExchange exchange, String id) throws IOException {
        int userId = Integer.parseInt(id);
        RegisteredUser user = users.get(userId);

        if (user != null) {
            String response = user.isARestaurantManager()
                    ? "User " + user.getName() + " is a Restaurant Manager."
                    : "User " + user.getName() + " is NOT a Restaurant Manager.";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            String response = "User Not Found";
            exchange.sendResponseHeaders(404, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public static void deleteUser(HttpExchange exchange, String id) throws IOException {
        int userId = Integer.parseInt(id);
        RegisteredUser user = users.remove(userId);

        if (user != null) {
            String response = "User Deleted: " + user.getName();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            String response = "User Not Found";
            exchange.sendResponseHeaders(404, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
