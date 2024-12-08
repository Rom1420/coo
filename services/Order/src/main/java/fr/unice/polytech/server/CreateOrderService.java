package fr.unice.polytech.server;

import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.server.httphandlers.OrderHttpHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

public class CreateOrderService {
    public static final int DEFAULT_PORT = 8002;

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(DEFAULT_PORT), 0);
            server.createContext("/api/order", new OrderHttpHandler());
            server.start();
            System.out.println("Order Service running on port " + DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
