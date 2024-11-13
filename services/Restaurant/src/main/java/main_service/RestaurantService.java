package main_service;

import com.sun.net.httpserver.HttpServer;
import filter.RestaurantFilterHandler;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Categorie;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;

import java.io.IOException;
import java.net.InetSocketAddress;
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

    public static void main(String[] args) {
        try {
            RestaurantManager restaurantManager = RestaurantManager.getRestaurantManagerInstance();

            List<Article> articles = new ArrayList<>();
            Article entree = new Article("Salade", 5.5f, 10, Categorie.ENTREE);
            Article plat = new Article("Steak", 15.0f, 20, Categorie.PLAT);
            Article dessert = new Article("Glace", 4.0f, 5, Categorie.DESSERT);
            articles.add(entree);
            articles.add(plat);
            articles.add(dessert);

            Restaurant leGourmet = new Restaurant("Le Gourmet");
            leGourmet.setSchedules(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(17, 0));
            leGourmet.setSchedules(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(18, 0));
            leGourmet.setArticlesSimples(articles);

            Restaurant laPizza = new Restaurant("La Pizza");
            laPizza.setSchedules(DayOfWeek.WEDNESDAY, LocalTime.of(12, 0), LocalTime.of(22, 0));

            Restaurant sushiWorld = new Restaurant("Sushi World");
            sushiWorld.setSchedules(DayOfWeek.THURSDAY, LocalTime.of(11, 0), LocalTime.of(20, 0));

            restaurantManager.addRestaurant(leGourmet);
            restaurantManager.addRestaurant(laPizza);
            restaurantManager.addRestaurant(sushiWorld);

            startServer(DEFAULT_PORT_FOR_RESTAURANT, restaurantManager);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start the server due to IO exception", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error", e);
        }
    }

    public static HttpServer startServer(int port, RestaurantManager restaurantRegistry) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/api/restaurant", new RestaurantHttpHandler(restaurantRegistry));
        server.createContext("/api/restaurant/filters", new RestaurantFilterHandler(restaurantRegistry));
        server.setExecutor(null);
        server.start();
        servers.put(port, server);
        logger.info("Restaurant Server started on port " + port);
        return server;
    }

    public static void stopServer(int port) {
        if (servers.get(port) != null) {
            servers.get(port).stop(0);
            logger.info("Restaurant Server stopped on port " + port);
        }
    }
}