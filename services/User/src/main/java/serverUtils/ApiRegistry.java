package serverUtils;

import com.sun.net.httpserver.HttpExchange;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiRegistry {
    private static final List<Route> routes = new ArrayList<>();

    public static void registerRoute(String method, String path, RouteHandler handler) {
        routes.add(new Route(method, path, handler));
        System.out.println("Route registered: " + method + " " + path);
    }

    public static void handleRequest(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            Route matchingRoute = findRoute(method, path);

            if (matchingRoute != null) {
                String id = extractPathParameter(path, matchingRoute.getPath());
                matchingRoute.getHandler().handle(exchange, id);
            } else {
                sendResponse(exchange, 404, "Route not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal Server Error: " + e.getMessage());
        }
    }

    private static Route findRoute(String method, String path) {
        for (Route route : routes) {
            if (route.getMethod().equalsIgnoreCase(method) && pathMatches(path, route.getPath())) {
                return route;
            }
        }
        return null;
    }

    private static boolean pathMatches(String requestPath, String routePath) {
        String[] requestParts = requestPath.split("/");
        String[] routeParts = routePath.split("/");

        if (requestParts.length != routeParts.length) return false;

        for (int i = 0; i < routeParts.length; i++) {
            if (!routeParts[i].startsWith("{") && !routeParts[i].equals(requestParts[i])) {
                return false;
            }
        }
        return true;
    }

    private static String extractPathParameter(String requestPath, String routePath) {
        String[] requestParts = requestPath.split("/");
        String[] routeParts = routePath.split("/");

        for (int i = 0; i < routeParts.length; i++) {
            if (routeParts[i].startsWith("{") && routeParts[i].endsWith("}")) {
                return requestParts[i];
            }
        }
        return null;
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}

