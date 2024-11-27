package filter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.restaurant.RestaurantManager;
import main_service.JaxsonUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleMenuFilterHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(ArticleMenuFilterHandler.class.getName());
    private final RestaurantManager restaurantManager;
    private final ArticleMenuFilterService articleMenuFilterService;

    public ArticleMenuFilterHandler(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
        this.articleMenuFilterService = new ArticleMenuFilterService();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        try {
            if (method.equals("GET")) {
                String path = exchange.getRequestURI().getPath();
                logger.info("Received request path: " + path);

                // Extraire le type d'entité depuis le contexte (article/menu)
                String context = exchange.getHttpContext().getPath();
                boolean isArticleFilter = context.contains("article");

                // Extraire les segments pour le restaurant
                String relativePath = path.replaceFirst("/api/restaurant/filters/(article|menu)", "").strip();
                String[] segments = relativePath.split("/");
                if (segments.length < 1) {
                    sendErrorResponse(exchange, 400, "Invalid URL format. Expected /{restaurantName}");
                    return;
                }

                String restaurantName = segments[1]; // Nom du restaurant

                // Extraire les paramètres de requête
                String query = exchange.getRequestURI().getQuery();
                Map<String, String> queryParams = parseQuery(query);
                if (queryParams.isEmpty()) {
                    sendErrorResponse(exchange, 400, "No filter parameters provided");
                    return;
                }

                logger.info("Restaurant: " + restaurantName + ", Filters: " + queryParams);

                // Traiter la requête
                handleFilterRequest(exchange, restaurantName, isArticleFilter, queryParams);
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.close();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error handling request", e);
            sendErrorResponse(exchange, 500, "Internal Server Error");
        }
    }


    private void handleFilterRequest(HttpExchange exchange, String restaurantName, boolean isArticleFilter, Map<String, String> queryParams) throws IOException {
        Restaurant restaurant = restaurantManager.findRestaurantByName(restaurantName);
        if (restaurant == null) {
            sendErrorResponse(exchange, 404, "Restaurant not found");
            return;
        }

        List<?> filteredResults;
        if (isArticleFilter) {
            filteredResults = filterArticles(restaurant, queryParams);
        } else {
            filteredResults = filterMenus(restaurant, queryParams);
        }

        String response = JaxsonUtils.toJson(filteredResults);

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private List<Article> filterArticles(Restaurant restaurant, Map<String, String> queryParams) {
        List<Article> articles = restaurant.getArticlesSimples();

        // Appliquer les filtres combinés
        if (queryParams.containsKey("price")) {
            float maxPrice = Float.parseFloat(queryParams.get("price"));
            articles = articleMenuFilterService.filterArticlesByPrice(articles, maxPrice);
        }

        if (queryParams.containsKey("time")) {
            int maxTime = Integer.parseInt(queryParams.get("time"));
            articles = articleMenuFilterService.filterArticlesByTime(articles, maxTime);
        }

        return articles;
    }


    private List<Menu> filterMenus(Restaurant restaurant, Map<String, String> queryParams) {
        List<Menu> menus = restaurant.getMenusOfRestaurant();

        if (queryParams.containsKey("price")) {
            float maxPrice = Float.parseFloat(queryParams.get("price"));
            menus = articleMenuFilterService.filterMenusByPrice(menus, maxPrice);
        }

        if (queryParams.containsKey("time")) {
            int maxTime = Integer.parseInt(queryParams.get("time"));
            menus = articleMenuFilterService.filterMenusByTime(menus, maxTime);
        }

        return menus;
    }


    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        String response = "{\"error\": \"" + message + "\"}";
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
    private Map<String, String> parseQuery(String query) {
        Map<String, String> queryParams = new HashMap<>();

        if (query == null || query.isEmpty()) {
            return queryParams;
        }

        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                queryParams.put(keyValue[0], keyValue[1]);
            }
        }

        return queryParams;
    }


}
