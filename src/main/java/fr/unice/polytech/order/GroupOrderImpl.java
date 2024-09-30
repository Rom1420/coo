package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;

import java.util.HashMap;
import java.util.Map;

public class GroupOrderImpl implements GroupOrderInterface {
    private int groupId;
    private Map<RegisteredUser, Order> usersOrders;

    private Restaurant restaurant;

    public GroupOrderImpl(int groupId) {
        this.groupId = groupId;
        usersOrders = new HashMap<>();
    }

    @Override
    public Order getOrder(RegisteredUser user) {
        return usersOrders.get(user);
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public void addOrUpdateUserOrder(RegisteredUser user, Order order) {
        usersOrders.put(user, order);
    }

    @Override
    public Map<RegisteredUser, Order> getUsersOrders() {
        return usersOrders;
    }

    @Override
    public void removeOrder(RegisteredUser user) {
        usersOrders.remove(user);
    }
}