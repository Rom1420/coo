package fr.unice.polytech.server;


import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.server.httphandlers.CreateGroupHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;

public class CreateGroupService {

    public static final int DEFAULT_PORT = 8001;

    static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("createGroupLogger");
    public static void main(String[] args) {
        try {
            startServer(DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpServer startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/group/create", new CreateGroupHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        logger.log(Level.INFO, "Create Group Server started on port "+port);
        return server;
    }

}
