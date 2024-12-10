package fr.unice.polytech.server.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.server.JaxsonUtils;
import fr.unice.polytech.server.microservices.JoinGroup;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

public class JoinGroupHandler implements HttpHandler {

    Logger logger = Logger.getLogger("JoinGroupHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Configuration des en-têtes CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        logger.info("JoinGroupHandler called");
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET":
                    logger.info("GET method called");
                    getGroupOrders(exchange);
                    break;
                case "POST":
                    logger.info("POST method called");
                    joinGroup(exchange);
                    break;
                case "OPTIONS":
                    exchange.sendResponseHeaders(HttpUtils.OK_CODE, -1);
                    break;
                default:
                    exchange.sendResponseHeaders(HttpUtils.BAD_REQUEST_CODE, 0);
                    exchange.close();
            }
        } catch (Exception e) {
            sendErrorResponse(exchange, HttpUtils.INTERNAL_SERVER_ERROR_CODE, "Internal Server Error");
        }
    }

    private void getGroupOrders(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.contains("groupId=")) {
            sendErrorResponse(exchange, HttpUtils.BAD_REQUEST_CODE, "Missing groupId in query parameters");
            return;
        }

        String groupIdParam = query.split("groupId=")[1];
        int groupId = Integer.parseInt(groupIdParam);
        List<Integer> orderIds = JoinGroup.getGroupOrderIds(groupId);

        String jsonResponse = JaxsonUtils.toJson(orderIds);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
        exchange.getResponseBody().write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }

    private void joinGroup(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String jsonBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        logger.info(jsonBody);

        JoinGroupRequest request = JaxsonUtils.fromJson(jsonBody, JoinGroupRequest.class);
        if (request == null || request.groupId() == null || request.orderId() == null || request.preparationTime() == null) {
            sendErrorResponse(exchange, HttpUtils.BAD_REQUEST_CODE, "Invalid request body");
            return;
        }

        JoinGroup.addOrderToGroup(request.groupId(), request.orderId(), request.preparationTime());

        String response = "{\"message\": \"Order added to group successfully\"}";
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    public record JoinGroupRequest(Integer groupId, Integer orderId, Integer preparationTime) {}
}
