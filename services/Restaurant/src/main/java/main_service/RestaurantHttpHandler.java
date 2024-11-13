package main_service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantHttpHandler implements HttpHandler {

    private final RestaurantManager restaurantManager;
    private static final Logger logger = Logger.getLogger(RestaurantHttpHandler.class.getName());

    public RestaurantHttpHandler(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "GET":
                    if (exchange.getRequestURI().getPath().equals("/api/restaurant")) {
                        logger.info("GET all restaurants");
                        answerWithAllRestaurants(exchange);
                    }
                    /*} else {
                        String id = extractIdFromPath(exchange.getRequestURI().getPath());
                        logger.info("GET restaurant with id " + id);
                        answerWithRestaurant(exchange, id);
                    }*/
                    break;
/*
                case "POST":
                    logger.info("POST method called");
                    if (exchange.getRequestURI().getPath().equals("/api/restaurant")) {
                        createRestaurant(exchange);
                    }
                    break;

                case "PUT":
                    String idToUpdate = extractIdFromPath(exchange.getRequestURI().getPath());
                    logger.info("PUT method for updating restaurant with id " + idToUpdate);
                    updateRestaurant(exchange, idToUpdate);
                    break;

                case "DELETE":
                    String idToDelete = extractIdFromPath(exchange.getRequestURI().getPath());
                    logger.info("DELETE method for deleting restaurant with id " + idToDelete);
                    deleteRestaurant(exchange, idToDelete);
                    break;
                */
                default:
                    logger.warning("Unknown HTTP method: " + method);
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    exchange.close();
            }
        } catch (Exception e) {
            sendErrorResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void answerWithAllRestaurants(HttpExchange exchange) throws IOException {
        List<Restaurant> restaurants = restaurantManager.consultRestaurant();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        String response = JaxsonUtils.toJson(restaurants);
        logger.log(Level.FINE, "Response: " + response);
        exchange.sendResponseHeaders(200, response.length());
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
