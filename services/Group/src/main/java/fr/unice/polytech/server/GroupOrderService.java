package fr.unice.polytech.server;


import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.server.httphandlers.RedirectHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupOrderService {

    public static final int DEFAULT_PORT4GROUP = 8000;

    static Logger logger = java.util.logging.Logger.getLogger("GroupOrderLogger");

    public static void main(String[] args)  {
        try {
            startServer(DEFAULT_PORT4GROUP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static HttpServer startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/group/create", new RedirectHandler("http://localhost:8001/api/group/create"));
        server.createContext("/api/group/join", new RedirectHandler("http://localhost:8002/api/group/join"));
        server.createContext("/api/group/validate", new RedirectHandler("http://localhost:8003/api/group/validate"));
        server.createContext("/api/group/payment", new RedirectHandler("http://localhost:8004/api/group/payment"));
        server.setExecutor(null); // Default executor
        server.start();
        logger.log(Level.INFO, "Create Group Server started on port "+port);
        return server;
    }


}
