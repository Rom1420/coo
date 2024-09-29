package fr.unice.polytech.biblio;

import java.util.HashMap;

public class OrderManager {

    private HashMap<Integer, Order> orders;

    private OrderManager() {
        orders = new HashMap<>();
    }

    // Singleton
    private static final OrderManager ORDER_MANAGER_INSTANCE = new OrderManager();

    public static OrderManager getOrderManagerInstance() {
        return ORDER_MANAGER_INSTANCE;
    }

    public void addOrder(Integer orderId, Order order) {
        orders.put(orderId, order);
    }

    public Order getOrder(Integer orderId) {
        return orders.get(orderId);
    }

    public void removeOrder(Integer orderId) {
        orders.remove(orderId);
    }
}

