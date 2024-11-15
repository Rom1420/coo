package fr.unice.polytech.server.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.GroupOrderManager;
import fr.unice.polytech.entities.DiscountType;
import fr.unice.polytech.entities.Restaurant;
import fr.unice.polytech.server.JaxsonUtils;
import fr.unice.polytech.server.microservices.CreateGroup;
import fr.unice.polytech.server.serverUtils.ApiRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateGroupHandler implements HttpHandler {

    Logger logger = Logger.getLogger("CreateGroupHandlerHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("CreateGroupHandler called");
        String method = exchange.getRequestMethod();
        logger.info(exchange.getRequestMethod());
        try {
            switch (method) {
                case "GET":
                    logger.info("GET method called");
                    answerWithAllGroupsIds(exchange);
                    break;
                case "POST":
                    logger.info("POST method called");
                    askToCreateGroup(exchange);
                    break;
                case "OPTIONS":
                    exchange.sendResponseHeaders(HttpUtils.OK_CODE, -1);
                    break;
                default:
                    exchange.sendResponseHeaders(HttpUtils.BAD_REQUEST_CODE, 0);
                    exchange.close();
            }
        } catch (IllegalArgumentException e) {
            sendErrorResponse(exchange, HttpUtils.BAD_REQUEST_CODE, e.getMessage());
        } catch (Exception e) {
            sendErrorResponse(exchange, HttpUtils.INTERNAL_SERVER_ERROR_CODE, "Internal Server Error");
        }
    }

    private void answerWithAllGroupsIds(HttpExchange exchange) throws IOException {
        List<Integer> groupIds = new ArrayList<>();
        groupIds.addAll(GroupOrderManager.getGroupOrderManagerInstance().getGroupOrders().keySet());
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = JaxsonUtils.toJson(groupIds);
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    private void askToCreateGroup(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String jsonBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        logger.info(jsonBody);
        GroupCreationRequest groupCreationRequest = JaxsonUtils.fromJson(jsonBody, GroupCreationRequest.class);
        if (groupCreationRequest == null) {
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();
            return;
        }

        RestaurantRequest restaurantRequest = groupCreationRequest.restaurant();
        if (restaurantRequest == null || restaurantRequest.name() == null || restaurantRequest.discountType() == null) {
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();
            return;
        }

        DiscountType discountType;
        try {
            discountType = DiscountType.valueOf(restaurantRequest.discountType().toUpperCase());
        } catch (IllegalArgumentException e) {
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();
            return;
        }

        Restaurant restaurant = new Restaurant(restaurantRequest.name(), discountType);
        Integer groupId = GroupOrderManager.getGroupOrderManagerInstance().getGroupOrderIdAndIncrease();

        CreateGroup.createGroup(groupId,
                restaurant,
                groupCreationRequest.deliveryDate(),
                groupCreationRequest.deliveryLocation());

        String response = "L'identifiant de votre groupe est: "+groupId;

        // Envoyer la réponse au client
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    public record GroupCreationRequest(
            RestaurantRequest restaurant,
            Date deliveryDate,
            String deliveryLocation) {
    }

    public record RestaurantRequest(
            String name,
            String discountType) {
    }
}
