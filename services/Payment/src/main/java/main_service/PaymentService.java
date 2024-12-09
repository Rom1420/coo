package main_service;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Random;

public class PaymentService {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8050), 0);
        server.createContext("/api/payment", new PaymentHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8050...");
    }

    static class PaymentHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
            if ("GET".equals(exchange.getRequestMethod())) {

                String response = getRandomPaymentStatus();

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // 405: Method Not Allowed
            }
        }

        private String getRandomPaymentStatus() {
            Random random = new Random();
            boolean isValid = random.nextBoolean();
            return isValid ? "valid payment" : "invalid payment";
        }
    }
}

