package fr.unice.polytech;

import java.util.HashMap;
import java.util.Map;

public class OrderService {

    private HashMap<Integer, String> order;

    private OrderService(){
        order = new HashMap<>();
    }

    private static final OrderService ORDER_SERVICE = new OrderService();

    public static OrderService getOrderService() {
        return ORDER_SERVICE;
    }

    public Map<Integer, String> getOrder() {
        return order;
    }
}
