package fr.unice.polytech.order;

import java.util.HashMap;
import java.util.Map;

public class GroupOrder {
    private int groupId;
    private Map<String, Order> usersOrders; // TODO : private Map<RegisteredUser, Order> userOrders

    public GroupOrder(){
        usersOrders = new HashMap<>();
    }

    public Order getOrder(String user){ // TODO : public getOrder(RegisteredUser user)
        return usersOrders.get(user);
    }
}
