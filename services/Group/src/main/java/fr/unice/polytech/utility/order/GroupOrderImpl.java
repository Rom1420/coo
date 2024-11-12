package fr.unice.polytech.utility.order;

import fr.unice.polytech.utility.discount.DiscountEngine;
import fr.unice.polytech.utility.discount.DiscountType;
import fr.unice.polytech.utility.restaurant.RestaurantManager;


import java.util.*;

public class GroupOrderImpl implements GroupOrderInterface {
    private int groupId;
    private Map<Integer, Order> usersOrders;

    private String restaurant;

    private Date deliveryDate;
    private String deliveryLocation;

    private List<Integer> userList;
    private String status;

    private DiscountEngine discountEngine;

    public GroupOrderImpl(int groupId) {
        this.groupId = groupId;
        this.usersOrders = new HashMap<>();
        this.userList = new ArrayList<>();
        this.status = "pending";
    }

    public GroupOrderImpl(int groupId, String restaurantName, Date deliveryDate, String deliveryLocation) {
        this.groupId = groupId;
        this.usersOrders = new HashMap<>();
        this.restaurant = restaurantName;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.userList = new ArrayList<>();
        this.status = "pending";
    }

    public void setGroupOrderRestaurant(String restaurant) {this.restaurant = restaurant;}

    public void setGroupOrderDeliveryDate(Date deliveryDate) {this.deliveryDate = deliveryDate;}

    public void setGroupOrderDeliveryLocation(String deliveryLocation) {this.deliveryLocation = deliveryLocation;}

    @Override
    public void addMember(Integer userId) { userList.add(userId); } // Vérifications dans proxy


    @Override
    public Order getOrder(Integer userId) {
        return usersOrders.get(userId);
    }

    public String getRestaurant() {
        return restaurant;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    public List<Integer> getUserList() {
        return userList;
    }
    @Override
    public void addOrUpdateUserOrder(Integer user, Order order) {
        if (!userList.contains(user)) {
            userList.add(user);
        }
        usersOrders.put(user, order);
    } // Vérifications dans proxy

    @Override
    public Map<Integer, Order> getUsersOrders() {
        return usersOrders;
    }

    @Override
    public void removeOrder(Integer user) {
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
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void closeOrder() {
        this.status = "closed";
    }

    public Map<Integer, Integer> calculateOrderHistory() {
        Map<Integer, Integer> orderHistory = new HashMap<>();

        for (Integer userId : getUserList()) {
            List<Order> userOrders = OrderManager.getOrderManagerInstance().getOrders(userId);

            int numberOfOrdersInCurrentRestaurant = (userOrders != null)
                    ? (int) userOrders.stream()
                    .filter(order -> order.getRestaurant().equals(RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(this.getRestaurant())))
                    .count()
                    : 0;

            orderHistory.put(userId, numberOfOrdersInCurrentRestaurant);
        }

        return orderHistory;
    }


    public void applyDiscount() {
        Map<Integer, Integer> orderHistory = (RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(getRestaurant()).getDiscountType() == DiscountType.LOYALTY) ? calculateOrderHistory() : new HashMap<>();
        discountEngine = new DiscountEngine();
        discountEngine.chooseStrategy(getRestaurant(), orderHistory);
        discountEngine.applyDiscount(this);
    }
}