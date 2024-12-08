package main_service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;

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
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
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
            // Sauvegarder les données
            RestaurantService.saveRestaurantsToJson(restaurantManager.consultRestaurant());

            String response = "Restaurant deleted successfully";
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            sendErrorResponse(exchange, 404, "Restaurant not found");
        }
    }

    private void answerWithAllRestaurants(HttpExchange exchange) throws IOException {
        List<Restaurant> restaurants = restaurantManager.consultRestaurant();

        // Préparer la réponse directement avec weeklySchedules converti pour JSON
        List<Map<String, Object>> response = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            Map<String, Object> restaurantData = new LinkedHashMap<>();
            restaurantData.put("name", restaurant.getName());
            restaurantData.put("articlesSimples", restaurant.getArticlesSimples());
            restaurantData.put("menusOfRestaurant", restaurant.getMenusOfRestaurant());
            restaurantData.put("weeklySchedules", RestaurantService.convertScheduleToJson(restaurant.getWeeklySchedules())); // Utilisation directe
            restaurantData.put("nbOfCook", restaurant.getNbOfCook());
            restaurantData.put("typeCuisine", restaurant.getTypeCuisine() != null ? restaurant.getTypeCuisine().name() : "AUTRE");
            restaurantData.put("discountType", restaurant.getDiscountType() != null ? restaurant.getDiscountType().name() : null);
            restaurantData.put("open", restaurant.isOpen());

            response.add(restaurantData);
        }

        String jsonResponse = JaxsonUtils.toJson(response);
        byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseBytes.length);
        exchange.getResponseBody().write(responseBytes);
        exchange.close();
    }


    private void answerWithRestaurant(HttpExchange exchange, String name) throws IOException {
        Restaurant restaurant = restaurantManager.findRestaurantByName(name);
        if(restaurant == null){
            sendErrorResponse(exchange, 404, "Restaurant not found");
            return;
        }

        Map<String, Object> restaurantData = new LinkedHashMap<>();
        restaurantData.put("name", restaurant.getName());
        restaurantData.put("articlesSimples", restaurant.getArticlesSimples());
        restaurantData.put("menusOfRestaurant", restaurant.getMenusOfRestaurant());
        restaurantData.put("weeklySchedules", RestaurantService.convertScheduleToJson(restaurant.getWeeklySchedules()));
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

        try {
            Restaurant newRestaurant = JaxsonUtils.fromJson(jsonBody, new TypeReference<Restaurant>() {});

            // Validation : Vérifier si le restaurant existe déjà
            if (restaurantManager.findRestaurantByName(newRestaurant.getName()) != null) {
                sendErrorResponse(exchange, 409, "Restaurant already exists");
                return;
            }

            // Ajouter le restaurant
            restaurantManager.addRestaurant(newRestaurant);

            // Sauvegarder les données
            RestaurantService.saveRestaurantsToJson(restaurantManager.consultRestaurant());

            String response = "Restaurant created successfully";
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(201, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to create restaurant", e);
            sendErrorResponse(exchange, 400, "Invalid restaurant data");
        }
    }



    private void updateRestaurant(HttpExchange exchange, String nameToUpdate) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        String jsonBody = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);

        logger.log(Level.FINE, "Request body: " + jsonBody);

        try {
            Restaurant updatedRestaurant = JaxsonUtils.fromJson(jsonBody, new TypeReference<Restaurant>() {});

            // Vérification : Le restaurant à mettre à jour existe-t-il ?
            Restaurant existingRestaurant = restaurantManager.findRestaurantByName(nameToUpdate);
            if (existingRestaurant == null) {
                sendErrorResponse(exchange, 404, "Restaurant not found");
                return;
            }

            // Mettre à jour les données
            restaurantManager.updateRestaurant(updatedRestaurant, nameToUpdate);

            // Sauvegarder les données
            RestaurantService.saveRestaurantsToJson(restaurantManager.consultRestaurant());

            String response = "Restaurant updated successfully";
            exchange.getResponseHeaders().set("Content-Type", "text/plain");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update restaurant", e);
            sendErrorResponse(exchange, 400, "Invalid restaurant data");
        }
    }



    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}
