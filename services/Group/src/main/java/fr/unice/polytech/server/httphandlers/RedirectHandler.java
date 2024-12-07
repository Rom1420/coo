package fr.unice.polytech.server.httphandlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class RedirectHandler implements HttpHandler {

    private final String targetBaseUrl;
    private static final Logger logger = Logger.getLogger("RedirectHandler");

    public RedirectHandler(String targetBaseUrl) {
        this.targetBaseUrl = targetBaseUrl;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.info("RedirectHandler called for: " + exchange.getRequestURI());

        String targetUrlWithQuery = targetBaseUrl;
        String queryString = exchange.getRequestURI().getQuery();
        if (queryString != null) {
            targetUrlWithQuery += "?" + queryString;
        }

        URL targetUrl = new URL(targetUrlWithQuery);
        HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
        connection.setRequestMethod(exchange.getRequestMethod());
        connection.setDoOutput(true);

        if ("POST".equals(exchange.getRequestMethod()) || "PUT".equals(exchange.getRequestMethod())) {
            byte[] requestBody = exchange.getRequestBody().readAllBytes();
            connection.getOutputStream().write(requestBody);
        }

        int responseCode = connection.getResponseCode();
        byte[] responseBody = connection.getInputStream().readAllBytes();

        exchange.sendResponseHeaders(responseCode, responseBody.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBody);
        }
    }
}