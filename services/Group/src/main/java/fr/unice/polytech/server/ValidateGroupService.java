package fr.unice.polytech.server;

import com.sun.net.httpserver.HttpServer;
import fr.unice.polytech.server.httphandlers.ValidateGroupHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidateGroupService {

    public static final int DEFAULT_PORT = 8003;

    static Logger logger = java.util.logging.Logger.getLogger("ValidateGroupLogger");

    public static void main(String[] args) {
        try {
            startServer(DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HttpServer startServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/api/group/validate", new ValidateGroupHandler());
        server.setExecutor(null);
        server.start();
        logger.log(Level.INFO, "Validate Group Server started on port " + port);
        return server;
    }



//public class ValidateGroupService {

   /* private final HashMap<Integer, GroupOrderProxy> groupOrders = GroupOrderService.getGroupOrderServiceInstance().getGroupOrders();

    public void validateGroupOrder(int groupOrderId) {
        GroupOrderProxy groupOrder = groupOrders.get(groupOrderId);
        if (groupOrder != null) {
            try {
                groupOrder.validateOrder();
                groupOrder.applyDiscount();
                groupOrder.closeOrder();
                System.out.println("Commande de groupe validée, fermée et envoyée en préparation");
            } catch (IllegalStateException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        } else {
            System.out.println("Erreur: Le groupe de commande n'existe pas");
        }
    }*/

}
