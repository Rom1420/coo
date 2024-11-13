package main_service;

import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.restaurant.RestaurantManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestaurantService {

    private static final Map<Integer, HttpServer> servers = new HashMap<>();

    static Logger logger = Logger.getLogger("RestaurantHandler");
    public static final int DEFAULT_PORT_FOR_RESTAURANT = 8000;

    public static void main(String[] args) {
        try {
            RestaurantManager restaurantManager = RestaurantManager.getRestaurantManagerInstance();
            startServer(DEFAULT_PORT_FOR_RESTAURANT, restaurantManager);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start the server", e);
        }
    }

    public static HttpServer startServer(int port, RestaurantManager restaurantRegistry) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/api/restaurant", new RestaurantHttpHandler(restaurantRegistry));
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