package main_service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;
import fr.unice.polytech.restaurant.ScheduleDTO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

                    } else {
                        String name = exchange.getRequestURI().getPath().substring("/api/restaurant/".length());
                        logger.info("GET restaurant with name " + name);
                        answerWithRestaurant(exchange, name);
                    }
                    break;

                case "POST":
                    logger.info("POST method called");
                    if (exchange.getRequestURI().getPath().equals("/api/restaurant")) {
                        createRestaurant(exchange);
                    }
                    break;

                case "PUT":
                    String nameToUpdate = exchange.getRequestURI().getPath().substring("/api/restaurant/".length());
                    logger.info("PUT method for updating restaurant with name " + nameToUpdate);
                    updateRestaurant(exchange, nameToUpdate);
                    break;

                case "DELETE":
                    String restaurantNameToDelete = exchange.getRequestURI().getPath().substring("/api/restaurant/".length());
                    logger.info("DELETE method for deleting restaurant with id " + restaurantNameToDelete);
                    deleteRestaurant(exchange, restaurantNameToDelete);
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

    private void deleteRestaurant(HttpExchange exchange, String restaurantNameToDelete) throws IOException {
        boolean success = restaurantManager.deleteRestaurant(restaurantNameToDelete);
        if (success) {
            String response = "Restaurant deleted";
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(201, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            sendErrorResponse(exchange, 404, "Restaurant not found");
        }
    }

    private void answerWithAllRestaurants(HttpExchange exchange) throws IOException {
        List<Restaurant> restaurants = restaurantManager.consultRestaurant();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Restaurant restaurant : restaurants) {
            List<ScheduleDTO> scheduleDTOs = restaurantManager.convertScheduleToDTO(restaurant.getWeeklySchedules());

            Map<String, Object> restaurantData = new LinkedHashMap<>();
            restaurantData.put("name", restaurant.getName());
            restaurantData.put("articlesSimples", restaurant.getArticlesSimples());
            restaurantData.put("menusOfRestaurant", restaurant.getMenusOfRestaurant());
            restaurantData.put("weeklySchedules", scheduleDTOs);
            restaurantData.put("nbOfCook", restaurant.getNbOfCook());
            restaurantData.put("typeCuisine", restaurant.getTypeCuisine() != null ? restaurant.getTypeCuisine().name() : "AUTRE");
            restaurantData.put("discountType", restaurant.getDiscountType() != null ? restaurant.getDiscountType().name() : null);
            restaurantData.put("open", restaurant.isOpen());
            response.add(restaurantData);
        }

        String jsonResponse = JaxsonUtils.toJson(response);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, jsonResponse.length());
        exchange.getResponseBody().write(jsonResponse.getBytes());
        exchange.close();
    }

    private void answerWithRestaurant(HttpExchange exchange, String name) throws IOException {
        Restaurant restaurant = restaurantManager.findRestaurantByName(name);
        if(restaurant == null){
            sendErrorResponse(exchange, 404, "Restaurant not found");
            return;
        }
        List<ScheduleDTO> scheduleDTOs = restaurantManager.convertScheduleToDTO(restaurant.getWeeklySchedules());

        Map<String, Object> restaurantData = new LinkedHashMap<>();
        restaurantData.put("name", restaurant.getName());
        restaurantData.put("articlesSimples", restaurant.getArticlesSimples());
        restaurantData.put("menusOfRestaurant", restaurant.getMenusOfRestaurant());
        restaurantData.put("weeklySchedules", scheduleDTOs);  // Ajouter les horaires convertis
        restaurantData.put("nbOfCook", restaurant.getNbOfCook());
        restaurantData.put("typeCuisine", restaurant.getTypeCuisine() != null ? restaurant.getTypeCuisine().name() : "AUTRE");
        restaurantData.put("discountType", restaurant.getDiscountType() != null ? restaurant.getDiscountType().name() : null);
        restaurantData.put("open", restaurant.isOpen());

        String response = JaxsonUtils.toJson(restaurantData);

        logger.log(Level.FINE, "Response: " + response);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
    private void createRestaurant(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        String jsonBody = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);

        logger.log(Level.FINE, "Request body: " + jsonBody);
        Restaurant newRestaurant = JaxsonUtils.fromJson(jsonBody, Restaurant.class);
        restaurantManager.addRestaurant(newRestaurant);

        String response = "Restaurant created";
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void updateRestaurant(HttpExchange exchange, String nameToUpdate) throws IOException{
        InputStream requestBody = exchange.getRequestBody();
        String jsonBody = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);

        logger.log(Level.FINE, "Request body: " + jsonBody);
        Restaurant updatedRestaurant = JaxsonUtils.fromJson(jsonBody, Restaurant.class);

        Restaurant existingRestaurant = restaurantManager.findRestaurantByName(nameToUpdate);
        if (existingRestaurant == null) {
            sendErrorResponse(exchange, 404, "Restaurant not found");
            return;
        }
        String oldRestaurantName = existingRestaurant.getName();

        restaurantManager.updateRestaurant(updatedRestaurant, oldRestaurantName);

        String response = "Restaurant updated successfully";
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, response.getBytes().length);
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
}
