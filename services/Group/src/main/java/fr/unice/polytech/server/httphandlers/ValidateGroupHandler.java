package fr.unice.polytech.server.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.db.GroupOrderManager;
import fr.unice.polytech.server.JaxsonUtils;
import fr.unice.polytech.server.microservices.ValidateGroup;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ValidateGroupHandler implements HttpHandler {

    Logger logger = Logger.getLogger("ValidateGroupHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Configuration des en-têtes CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        logger.info("ValidateGroupHandler called");
        String method = exchange.getRequestMethod();
        logger.info("HTTP Method: " + method);

        try {
            switch (method) {
                case "GET":
                    logger.info("GET method called");
                    answerWithAllGroupsStatus(exchange);
                    break;
                case "POST":
                    logger.info("POST method called");
                    validateSpecificGroup(exchange);
                    break;
                case "OPTIONS":
                    exchange.sendResponseHeaders(200, -1); // Préflight request
                    break;
                default:
                    exchange.sendResponseHeaders(400, 0);
                    exchange.close();
            }
        } catch (Exception e) {
            sendErrorResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void answerWithAllGroupsStatus(HttpExchange exchange) throws IOException {
        var groupStatuses = new java.util.HashMap<Integer, String>();
        GroupOrderManager.getGroupOrderManagerInstance().getGroupOrders()
                .forEach((id, group) -> groupStatuses.put(id, group.getStatus()));
        String response = JaxsonUtils.toJson(groupStatuses);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void validateSpecificGroup(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("groupId=")) {
            sendErrorResponse(exchange, 400, "Missing or invalid groupId parameter");
            return;
        }

        int groupId;
        try {
            groupId = Integer.parseInt(query.split("=")[1]);
        } catch (NumberFormatException e) {
            sendErrorResponse(exchange, 400, "Invalid groupId format");
            return;
        }

        boolean success = ValidateGroup.validateGroup(groupId);
        if (success) {
            GroupOrderManager.getGroupOrderManagerInstance().updateGroupStatusInDB(groupId, "validated");

            String response = "Group " + groupId + " validated successfully";
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            sendErrorResponse(exchange, 400, "Group validation failed");
        }
    }


    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
