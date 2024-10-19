package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;

import java.util.*;

public class GroupOrderImpl implements GroupOrderInterface {
    private int groupId;
    private Map<RegisteredUser, Order> usersOrders;

    private Restaurant restaurant;

    private Date deliveryDate;
    private String deliveryLocation;

    private List<RegisteredUser> userList;
    private String status;


    public GroupOrderImpl(int groupId, Restaurant restaurant, Date deliveryDate, String deliveryLocation) {
        this.groupId = groupId;
        usersOrders = new HashMap<>();
        this.restaurant = restaurant;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.userList = new ArrayList<>();
        this.status = "pending";
    }

    @Override
    public void addMember(RegisteredUser user) {
        userList.add(user);
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

    public List<RegisteredUser> getUserList() {
        return userList;
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

    @Override
    public String getGroupOrderDeliveryLocation() {
        return this.deliveryLocation;

    }

    public void validateOrder() {
        this.status = "validated";
        System.out.println("La commande de groupe a été validée");
    }

    public String getStatus() {
        return status;
    }

    public void closeOrder() {
        this.status = "closed";
    }
}