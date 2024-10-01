package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GroupOrderImpl implements GroupOrderInterface {
    private int groupId;
    private Map<RegisteredUser, Order> usersOrders;

    private Restaurant restaurant;

    private Date deliveryDate;
    private String deliveryLocation;

    public GroupOrderImpl(int groupId, Restaurant restaurant, Date deliveryDate, String deliveryLocation) {
        this.groupId = groupId;
        usersOrders = new HashMap<>();
        this.restaurant = restaurant;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
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

    @Override
    public int getTotalPreparationTime() {
        return getUsersOrders().values().stream()
                .mapToInt(Order::getTotalPreparationTime)
                .sum();
    }

    @Override
    public Date getGroupOrderDeliveryDate() {
        return this.deliveryDate;
    }

}