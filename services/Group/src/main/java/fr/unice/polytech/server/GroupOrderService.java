package fr.unice.polytech.server;


import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.server.httphandlers.RedirectHandler;
import java.io.IOException;
import java.net.InetSocketAddress;

public class GroupOrderService {

    public static final int DEFAULT_PORT4GROUP = 8000;
    public static void main(String[] args)  {
        try {
            startServer(DEFAULT_PORT4GROUP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static HttpServer startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/group/create", new RedirectHandler("http://localhost:8081/api/group/create"));
        server.createContext("/api/group/join", new RedirectHandler("http://localhost:8082/api/group/join"));
        server.createContext("/api/group/validate", new RedirectHandler("http://localhost:8083/api/group/validate"));
        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("Server A started on port 8080");
        return server;
    }


}
