package fr.unice.polytech.server.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.components.OrderImpl;
import fr.unice.polytech.db.OrderManager;
import fr.unice.polytech.server.JaxsonUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class OrderHttpHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(OrderHttpHandler.class.getName());
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        try {
            switch (method) {
                case "POST":
                    createOrder(exchange);
                    break;
                case "GET":
                    getOrder(exchange);
                    break;
                case "OPTIONS":
                    exchange.sendResponseHeaders(204, -1);
                    exchange.close();
                    break;
                default:
                    logger.warning("Unknown HTTP method: " + method);
                    exchange.sendResponseHeaders(405, 0);
                    exchange.close();
            }
        } catch (Exception e) {
            sendErrorResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void createOrder(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        OrderImpl order = JaxsonUtils.fromJson(requestBody, OrderImpl.class);
        OrderManager.getOrderManagerInstance().addOrder(order);

        String response = "Order created with ID: " + order.getId();
        exchange.sendResponseHeaders(201, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    private void getOrder(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        int id = Integer.parseInt(query.split("=")[1]);

        OrderImpl order = OrderManager.getOrderManagerInstance().getOrder(id);
        if (order == null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        String response = JaxsonUtils.toJson(order);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}

