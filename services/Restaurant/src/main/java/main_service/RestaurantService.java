package main_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.net.httpserver.HttpServer;
import filter.RestaurantFilterHandler;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantService {

    private static final Map<Integer, HttpServer> servers = new HashMap<>();
    static Logger logger = Logger.getLogger("RestaurantHandler");
    public static final int DEFAULT_PORT_FOR_RESTAURANT = 8080;
    private static final String DATABASE_FILE = "src/main/resources/restaurants.json";


    public static void main(String[] args) {
        try {
            // Chargement des restaurants depuis le JSON
            List<Restaurant> restaurants = loadRestaurantsFromJson(DATABASE_FILE);

            // Initialisation du RestaurantManager
            RestaurantManager restaurantManager = RestaurantManager.getRestaurantManagerInstance();
            if (restaurants != null) {
                restaurants.forEach(restaurantManager::addRestaurant);
                logger.info("Restaurants loaded and added to the manager.");
            }

            // Démarrage du serveur
            startServer(DEFAULT_PORT_FOR_RESTAURANT, restaurantManager);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start the server due to IO exception", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error", e);
        }
    }

    private static List<Restaurant> loadRestaurantsFromJson(String filePath) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            return JaxsonUtils.fromJson(json, new TypeReference<List<Restaurant>>() {});
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load restaurants from JSON file", e);
            return null;
        }
    }

    public static void saveRestaurantsToJson(List<Restaurant> restaurants) {
        try {
            // Préparer les données pour la sérialisation
            List<Map<String, Object>> processedRestaurants = new ArrayList<>();

            for (Restaurant restaurant : restaurants) {
                Map<String, Object> restaurantData = new HashMap<>();
                restaurantData.put("name", restaurant.getName());
                restaurantData.put("articlesSimples", restaurant.getArticlesSimples());
                restaurantData.put("menusOfRestaurant", restaurant.getMenusOfRestaurant());
                restaurantData.put("weeklySchedules", convertScheduleToJson(restaurant.getWeeklySchedules()));
                restaurantData.put("nbOfCook", restaurant.getNbOfCook());
                restaurantData.put("typeCuisine", restaurant.getTypeCuisine() != null ? restaurant.getTypeCuisine().name() : null);
                restaurantData.put("discountType", restaurant.getDiscountType() != null ? restaurant.getDiscountType().name() : null);
                restaurantData.put("open", restaurant.isOpen());

                processedRestaurants.add(restaurantData);
            }

            // Convertir les restaurants en JSON
            String json = JaxsonUtils.toJson(processedRestaurants);

            // Sauvegarder dans le fichier
            File file = new File(DATABASE_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Crée les dossiers si nécessaire
                file.createNewFile();
            }

            Files.writeString(file.toPath(), json, StandardOpenOption.TRUNCATE_EXISTING);

            Logger.getLogger(RestaurantService.class.getName()).info("Restaurants successfully saved to JSON.");
        } catch (JsonProcessingException e) {
            Logger.getLogger(RestaurantService.class.getName()).log(Level.SEVERE, "Failed to convert restaurants to JSON.", e);
        } catch (IOException e) {
            Logger.getLogger(RestaurantService.class.getName()).log(Level.SEVERE, "Failed to write JSON to file.", e);
        }
    }


    public static HttpServer startServer(int port, RestaurantManager restaurantManager) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/api/restaurant", new RestaurantHttpHandler(restaurantManager));
        server.createContext("/api/restaurant/filters", new RestaurantFilterHandler(restaurantManager));
        server.setExecutor(null); // Utilisation du gestionnaire par défaut
        server.start();
        servers.put(port, server);
        logger.info("Restaurant Server started on port " + port);
        return server;
    }

    public static void stopServer(int port) {
        HttpServer server = servers.get(port);
        if (server != null) {
            server.stop(0);
            logger.info("Restaurant Server stopped on port " + port);
        }
    }

    public static Map<String, Map<String, String>> convertScheduleToJson(Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> schedules) {
        Map<String, Map<String, String>> jsonSchedules = new HashMap<>();
        for (Map.Entry<DayOfWeek, Map.Entry<LocalTime, LocalTime>> entry : schedules.entrySet()) {
            String day = entry.getKey().name();
            String opening = entry.getValue().getKey().toString();
            String closing = entry.getValue().getValue().toString();

            Map<String, String> timeMap = new HashMap<>();
            timeMap.put("opening", opening);
            timeMap.put("closing", closing);

            jsonSchedules.put(day, timeMap);
        }
        return jsonSchedules;
    }

}
