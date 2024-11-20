package fr.unice.polytech.server;

import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.server.httphandlers.PaymentHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class PaymentService {

    public static final int DEFAULT_PORT = 8004;
    private static final Logger logger = Logger.getLogger("PaymentService");

    public static void main(String[] args) {
        try {
            startServer(DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpServer startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/group/payment", new PaymentHandler());
        server.setExecutor(null); // Default executor
        server.start();
        logger.info("Payment Service started on port " + port);
        return server;
    }

}
