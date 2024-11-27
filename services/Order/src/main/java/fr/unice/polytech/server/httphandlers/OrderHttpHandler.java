package fr.unice.polytech.utility.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.unice.polytech.OrderService;
import fr.unice.polytech.utility.serverUtils.ApiRegistry;
import fr.unice.polytech.utility.serverUtils.RouteInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * This class is used to manage an order.
 * It handles the following requests:
 *  - GET /api/order : return the order
 *  - POST /api/order/{menu} : add a menu
 *  - POST /api/order/{article} : add an article
 *  - PUT /api/order/{menu} : update a menu
 *  - PUT /api/order/{article} : update an article
 *  - DELETE /api/order/{menu} : delete a menu
 *  - DELETE /api/order/{article} : delete an article
 */
public class OrderHttpHandler implements HttpHandler {

    private final OrderService orderService;
    Logger logger = Logger.getLogger("OrderHandler");

    public OrderHttpHandler(OrderService orderService) {
        this.orderService = orderService;

        ApiRegistry.registerRoute("GET", "/api/order", (exchange, param) -> {
            logger.log(java.util.logging.Level.FINE, "GET order");
            answerWithOrder(exchange);
        });

        ApiRegistry.registerRoute("POST", "/api/order/{menu}", (exchange, menu) -> {
            logger.log(java.util.logging.Level.FINE, "POST add menu " + menu);
            addMenuToOrder(exchange, menu);
        });

        ApiRegistry.registerRoute("POST", "/api/order/{article}", (exchange, article) -> {
            logger.log(java.util.logging.Level.FINE, "POST add article " + article);
            addArticleToOrder(exchange, article);
        });

        ApiRegistry.registerRoute("PUT", "/api/order/{menu}", (exchange, menu) -> {
            logger.log(java.util.logging.Level.FINE, "PUT update menu " + menu);
            updateMenuInOrder(exchange, menu);
        });

        ApiRegistry.registerRoute("PUT", "/api/order/{article}", (exchange, article) -> {
            logger.log(java.util.logging.Level.FINE, "PUT update article " + article);
            updateArticleInOrder(exchange, article);
        });

        ApiRegistry.registerRoute("DELETE", "/api/order/{menu}", (exchange, menu) -> {
            logger.log(java.util.logging.Level.FINE, "DELETE menu " + menu);
            deleteMenuFromOrder(exchange, menu);
        });

        ApiRegistry.registerRoute("DELETE", "/api/order/{article}", (exchange, article) -> {
            logger.log(java.util.logging.Level.FINE, "DELETE article " + article);
            deleteArticleFromOrder(exchange, article);
        });
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        String requestMethod = exchange.getRequestMethod();
        String requestPath = exchange.getRequestURI().getPath();
        logger.log(java.util.logging.Level.INFO, "OrderHandler called: " + requestMethod + " " + requestPath);

        for (RouteInfo route : ApiRegistry.getRoutes()) {
            if (route.matches(requestMethod, requestPath)) {
                Matcher matcher = route.getPathMatcher(requestPath);
                String param = matcher.find() && matcher.groupCount() > 0 ? matcher.group(1) : "";
                route.getHandler().handle(exchange, param);
                return;
            }
        }

        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseBody().close();
    }

    private void answerWithOrder(HttpExchange exchange) throws IOException {
        String response = orderService.getOrderDetails();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void addMenuToOrder(HttpExchange exchange, String menu) throws IOException {
        orderService.addMenu(menu);
        String response = "Menu added to order.";
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(201, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void addArticleToOrder(HttpExchange exchange, String article) throws IOException {
        orderService.addArticle(article);
        String response = "Article added to order.";
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(201, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void updateMenuInOrder(HttpExchange exchange, String menu) throws IOException {
        InputStream is = exchange.getRequestBody();
        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        orderService.updateMenu(menu, json);
        String response = "Menu updated in order.";
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void updateArticleInOrder(HttpExchange exchange, String article) throws IOException {
        InputStream is = exchange.getRequestBody();
        String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        orderService.updateArticle(article, json);
        String response = "Article updated in order.";
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void deleteMenuFromOrder(HttpExchange exchange, String menu) throws IOException {
        orderService.deleteMenu(menu);
        exchange.sendResponseHeaders(204, 0);
        exchange.getResponseBody().close();
    }

    private void deleteArticleFromOrder(HttpExchange exchange, String article) throws IOException {
        orderService.deleteArticle(article);
        exchange.sendResponseHeaders(204, 0);
        exchange.getResponseBody().close();
    }
}
