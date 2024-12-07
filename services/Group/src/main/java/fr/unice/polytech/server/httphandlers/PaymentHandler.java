package fr.unice.polytech.server.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.logging.Logger;

public class PaymentHandler implements HttpHandler {

    private final Random random = new Random();
    private static final Logger logger = Logger.getLogger("PaymentHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("PaymentHandler called");
        String method = exchange.getRequestMethod();
        if ("POST".equalsIgnoreCase(method)) {
            String query = exchange.getRequestURI().getQuery();
            if (query == null || !query.startsWith("orderId=")) {
                sendErrorResponse(exchange, 400, "Missing or invalid orderId parameter");
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(query.split("=")[1]);
            } catch (NumberFormatException e) {
                sendErrorResponse(exchange, 400, "Invalid orderId format");
                return;
            }

            // Générer un résultat de paiement aléatoire
            boolean paymentSuccess = random.nextBoolean();
            String response = paymentSuccess ? "Payment validated for order " + orderId : "Payment failed for order " + orderId;

            int statusCode = paymentSuccess ? 200 : 400;
            exchange.sendResponseHeaders(statusCode, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Méthode non autorisée
        }
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}