package fr.unice.polytech.server.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.components.OrderImpl;
import fr.unice.polytech.db.OrderManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class OrderHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "POST":
                createOrder(exchange);
                break;
            case "GET":
                getOrder(exchange);
                break;
            default:
                exchange.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void createOrder(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        OrderImpl order = fr.unice.polytech.utility.server.JaxsonUtils.fromJson(requestBody, OrderImpl.class);
        OrderManager.getOrderManagerInstance().addOrder(order);

        String response = "Order created with ID: " + order.getOrderId();
        exchange.sendResponseHeaders(201, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    private void getOrder(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        int orderId = Integer.parseInt(query.split("=")[1]);

        OrderImpl order = OrderManager.getOrderManagerInstance().getOrder(orderId);
        if (order == null) {
            exchange.sendResponseHeaders(404, -1);
            return;
        }

        String response = fr.unice.polytech.utility.server.JaxsonUtils.toJson(order);
        exchange.sendResponseHeaders(200, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}

