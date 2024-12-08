package fr.unice.polytech.server.httphandlers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.components.GroupOrderProxy;
import fr.unice.polytech.db.GroupOrderManager;
import fr.unice.polytech.entities.DiscountType;
import fr.unice.polytech.entities.Restaurant;
import fr.unice.polytech.server.JaxsonUtils;
import fr.unice.polytech.server.microservices.CreateGroup;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

public class CreateGroupHandler implements HttpHandler {

    Logger logger = Logger.getLogger("CreateGroupHandlerHandler");

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Configuration des en-têtes CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        logger.info("CreateGroupHandler called");
        String method = exchange.getRequestMethod();
        logger.info(exchange.getRequestMethod());
        try {
            switch (method) {
                case "GET":
                    logger.info("GET method called");
                    answerWithAllGroups(exchange);
                    break;
                case "POST":
                    logger.info("POST method called");
                    askToCreateGroup(exchange);
                    break;
                case "OPTIONS":
                    exchange.sendResponseHeaders(fr.unice.polytech.server.httphandlers.HttpUtils.OK_CODE, -1);
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

    private void answerWithAllGroups(HttpExchange exchange) throws IOException {
        // Configuration des en-têtes CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        Map<Integer, GroupOrderProxy> groupOrders = GroupOrderManager.getGroupOrderManagerInstance().getGroupOrders();
        List<GroupOrderProxy> groupDetailsList = new ArrayList<>(groupOrders.values());
        String jsonResponse = JaxsonUtils.toJson(groupDetailsList);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
        exchange.getResponseBody().write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }


    private void askToCreateGroup(HttpExchange exchange) throws IOException {
        // Configuration des en-têtes CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

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

        CreateGroup.createGroup(
                groupId,
                groupCreationRequest.groupName(),
                restaurant,
                groupCreationRequest.deliveryDate(),
                groupCreationRequest.deliveryLocation());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", groupId);
        responseMap.put("groupName", groupCreationRequest.groupName());

        String jsonResponse = JaxsonUtils.toJson(responseMap);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(201, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        // Configuration des en-têtes CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }

    public record GroupCreationRequest(
            String groupName,
            RestaurantRequest restaurant,
            Date deliveryDate,
            String deliveryLocation) {
    }

    public record RestaurantRequest(
            String name,
            String discountType) {
    }
}
