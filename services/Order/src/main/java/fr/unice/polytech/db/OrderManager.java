package fr.unice.polytech.db;

import fr.unice.polytech.components.OrderImpl;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class OrderManager {
    private static final OrderManager ORDER_MANAGER_INSTANCE = new OrderManager();
    private static final String ORDER_DB_PATH = "orderDB.json";
    private static final String ORDER_ID_DB_PATH = "orderIdDB.json";

    private final HashMap<Integer, OrderImpl> orders;
    private int currentOrderId;

    private OrderManager() {
        orders = new HashMap<>();
        loadOrdersFromFile();
    }

    public static OrderManager getOrderManagerInstance() {
        return ORDER_MANAGER_INSTANCE;
    }

    public void addOrder(OrderImpl order) {
        currentOrderId++;
        order.setId(currentOrderId);
        orders.put(currentOrderId, order);
        saveOrdersToFile();
    }

    public OrderImpl getOrder(int id) {
        return orders.get(id);
    }

    private void loadOrdersFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Charger les commandes depuis le fichier JSON
            File orderFile = new File(ORDER_DB_PATH);
            if (orderFile.exists()) {
                HashMap<Integer, OrderImpl> loadedOrders = mapper.readValue(orderFile, new TypeReference<>() {});
                System.out.println(loadedOrders.get(1).getMenus());
                orders.putAll(loadedOrders);
            }

            // Charger l'ID courant depuis le fichier JSON
            File idFile = new File(ORDER_ID_DB_PATH);
            if (idFile.exists()) {
                currentOrderId = mapper.readValue(idFile, Integer.class);
            } else {
                currentOrderId = 0;
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement des fichiers : " + e.getMessage());
        }
    }

    private void saveOrdersToFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {

            // Sauvegarder les commandes dans le fichier JSON
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ORDER_DB_PATH), orders);

            // Sauvegarder l'ID courant dans un fichier séparé
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(ORDER_ID_DB_PATH), currentOrderId);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde des fichiers : " + e.getMessage());
        }
    }
}



