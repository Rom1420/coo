package filter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;
import fr.unice.polytech.restaurant.TypeCuisine;
import main_service.JaxsonUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Logger;

public class RestaurantFilterHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(RestaurantFilterHandler.class.getName());
    private final RestaurantManager restaurantManager;
    private final RestaurantFilterService restaurantFilterService;

    public RestaurantFilterHandler(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
        this.restaurantFilterService = new RestaurantFilterService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            if (method.equals("GET")) {
                String path = exchange.getRequestURI().getPath();
                if (path.equals("/api/restaurant/filters")) {
                    applyFilters(exchange);
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
            }
        } catch (Exception e) {
            sendErrorResponse(exchange, 500, "Internal Server Error");
        }
    }

    private void applyFilters(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String[] params = query.split("&");

        TypeCuisine cuisineType = null;
        DayOfWeek day = null;
        LocalTime time = null;

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                switch (keyValue[0]) {
                    case "cuisineType":
                        cuisineType = TypeCuisine.valueOf(keyValue[1].toUpperCase());
                        break;
                    case "day":
                        day = DayOfWeek.valueOf(keyValue[1].toUpperCase());
                        break;
                    case "time":
                        time = LocalTime.parse(keyValue[1]);
                        break;
                }
            }
        }

        List<Restaurant> restaurants = restaurantManager.consultRestaurant();

        List<Restaurant> filteredRestaurants = restaurantFilterService.applyFilters(restaurants, cuisineType, day, time);

        String response = JaxsonUtils.toJson(filteredRestaurants);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
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
