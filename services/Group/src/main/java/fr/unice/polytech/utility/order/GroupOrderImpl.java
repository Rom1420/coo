package fr.unice.polytech.utility.order;

import fr.unice.polytech.utility.discount.DiscountEngine;
import fr.unice.polytech.utility.discount.DiscountType;
import fr.unice.polytech.utility.restaurant.Restaurant;
import fr.unice.polytech.utility.user.RegisteredUser;

import java.util.*;

public class GroupOrderImpl implements GroupOrderInterface {
    private int groupId;
    private Map<RegisteredUser, Order> usersOrders;

    private Restaurant restaurant;

    private Date deliveryDate;
    private String deliveryLocation;

    private List<RegisteredUser> userList;
    private String status;

    private DiscountEngine discountEngine;
  
    //Default constructor, for testing
    public GroupOrderImpl(int groupId) {
        this.groupId = groupId;
        this.usersOrders = new HashMap<>();
        this.userList = new ArrayList<>();
        this.status = "pending";
    }

    public GroupOrderImpl(int groupId, Restaurant restaurant, Date deliveryDate, String deliveryLocation) {
        this.groupId = groupId;
        this.usersOrders = new HashMap<>();
        this.restaurant = restaurant;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.userList = new ArrayList<>();
        this.status = "pending";
    }

    public void setGroupOrderRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}

    public void setGroupOrderDeliveryDate(Date deliveryDate) {this.deliveryDate = deliveryDate;}

    public void setGroupOrderDeliveryLocation(String deliveryLocation) {this.deliveryLocation = deliveryLocation;}

    @Override
    public void addMember(RegisteredUser user) {
        if (this.getStatus().equals("validated")) {
            throw new IllegalStateException("Impossible d'ajouter un membre car le groupe est fermé");
        }
        if (!userList.contains(user)) {
            userList.add(user);
        }
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
        if (this.getStatus().equals("validated")) {
            throw new IllegalStateException("Impossible d'ajouter ou de modifier une commande car le groupe est fermé");
        }

        if (this.restaurant != order.getRestaurant() || this.deliveryDate != order.getDeliveryDate() || !Objects.equals(this.deliveryLocation, order.getDeliveryLocation())) {
            throw new IllegalStateException("Les paramètres de la commande ne correspondent pas à ceux du groupe");
        }

        if (!userList.contains(user)) {
            userList.add(user);
        }

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

    public Map<RegisteredUser, Integer> calculateOrderHistory() {
        Map<RegisteredUser, Integer> orderHistory = new HashMap<>();
        Restaurant currentRestaurant = this.getRestaurant();

        for (RegisteredUser user : getUserList()) {
            List<Order> userOrders = OrderManager.getOrderManagerInstance().getOrders(user.getId());

            int numberOfOrdersInCurrentRestaurant = (userOrders != null)
                    ? (int) userOrders.stream()
                    .filter(order -> order.getRestaurant().equals(currentRestaurant))
                    .count()
                    : 0;

            orderHistory.put(user, numberOfOrdersInCurrentRestaurant);
        }

        return orderHistory;
    }


    public void applyDiscount() {
        Map<RegisteredUser, Integer> orderHistory = (restaurant.getDiscountType() == DiscountType.LOYALTY) ? calculateOrderHistory() : new HashMap<>();
        discountEngine = new DiscountEngine();
        discountEngine.chooseStrategy(this.getRestaurant(), orderHistory);
        discountEngine.applyDiscount(this);
    }
}