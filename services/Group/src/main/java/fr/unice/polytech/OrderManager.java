package fr.unice.polytech;

import fr.unice.polytech.entities.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderManager {

    private final HashMap<Integer, ArrayList<Order>> orders;

    public OrderManager() {
        orders = new HashMap<>();
    }

    // Singleton
    private static final OrderManager ORDER_MANAGER_INSTANCE = new OrderManager();

    public static OrderManager getOrderManagerInstance() {
        return ORDER_MANAGER_INSTANCE;
    }

    public void addOrder(Integer userId, Order order) {
        if(orders.get(userId) == null){
            orders.put(userId, new ArrayList<>());
            orders.get(userId).add(order);
        }
        else{
            orders.get(userId).add(order);
        }
    }

    public List<Order> getOrders(Integer userId) {
        return orders.get(userId);
    }

    public void removeOrder(Integer userId) {
        orders.remove(userId);
    }
}

