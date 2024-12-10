package fr.unice.polytech.components;




import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.db.GroupOrderManager;
import fr.unice.polytech.db.OrderManager;
import fr.unice.polytech.entities.DiscountType;
import fr.unice.polytech.entities.Order;
import fr.unice.polytech.entities.Restaurant;
import fr.unice.polytech.interfaces.GroupOrderInterface;

import java.util.*;

public class GroupOrderImpl implements GroupOrderInterface {
    private int groupId;

    private String groupName;

    private List<Integer> usersOrders;

    private Restaurant restaurant;

    private Date deliveryDate;
    private String deliveryLocation;

    private List<Integer> userList;
    private String status;
    private int totalPreparationTime;

    private DiscountEngine discountEngine;
  
    //Default constructor, for testing
    public GroupOrderImpl(int groupId) {
        this.groupId = groupId;
        this.usersOrders = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.status = "pending";
    }

    public GroupOrderImpl() {
        this.usersOrders = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.status = "pending";
    }

    public GroupOrderImpl(int groupId, String groupName, Restaurant restaurant, Date deliveryDate, String deliveryLocation) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.usersOrders = new ArrayList<>();
        this.restaurant = restaurant;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.userList = new ArrayList<>();
        this.status = "pending";
        this.totalPreparationTime = 0;
    }



    @JsonCreator
    public GroupOrderImpl(
            @JsonProperty("groupId") int groupId,
            @JsonProperty("groupName") String groupName,
            @JsonProperty("usersOrders") List<Integer> usersOrders,
            @JsonProperty("restaurant") Restaurant restaurant,
            @JsonProperty("groupOrderDeliveryDate") Date deliveryDate,
            @JsonProperty("groupOrderDeliveryLocation") String deliveryLocation,
            @JsonProperty("userList") List<Integer> userList,
            @JsonProperty("status") String status,
            @JsonProperty("totalPreparationTime") int totalPreparationTime){
        this.groupId = groupId;
        this.groupName = groupName;
        this.usersOrders = usersOrders != null ? usersOrders : new ArrayList<>();
        this.restaurant = restaurant;
        this.deliveryDate = deliveryDate;
        this.deliveryLocation = deliveryLocation;
        this.userList = userList != null ? userList : new ArrayList<>();
        this.status = status;
        this.discountEngine = new DiscountEngine();
        this.totalPreparationTime = totalPreparationTime;
    }

    public void setGroupName(String groupName) {this.groupName = groupName;}

    public String getGroupName() {return this.groupName;}

    public void setGroupOrderRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}

    public void setGroupOrderDeliveryDate(Date deliveryDate) {this.deliveryDate = deliveryDate;}

    public void setGroupOrderDeliveryLocation(String deliveryLocation) {this.deliveryLocation = deliveryLocation;}

    @Override
    public void addMember(Integer user) { userList.add(user); }

    @Override
    public Integer getOrder(Integer id) {
        return usersOrders.get(id - 1);
    }

    public Restaurant getRestaurant() {
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
    public void addOrUpdateUserOrder(Integer orderId, Integer preparationTime) {
        if (!usersOrders.contains(orderId)) {
            usersOrders.add(orderId);
            totalPreparationTime += preparationTime;
        }
    }

    @Override
    public List<Integer> getUsersOrders() {
        return usersOrders;
    }

    @Override
    public void removeOrder(Integer user) {
        usersOrders.remove(user);
    }

    @Override
    public int getTotalPreparationTime() {
        return totalPreparationTime;
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

    public void setStatus(String status) { this.status = status; }

    public void closeOrder() {
        this.status = "closed";
    }

    public Map<Integer, Integer> calculateOrderHistory() {
        Map<Integer, Integer> orderHistory = new HashMap<>();
        Restaurant currentRestaurant = this.getRestaurant();

        for (Integer user : getUserList()) {
            List<Order> userOrders = OrderManager.getOrderManagerInstance().getOrders(user);

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
        Map<Integer, Integer> orderHistory = (restaurant.getDiscountType() == DiscountType.LOYALTY) ? calculateOrderHistory() : new HashMap<>();
        discountEngine = new DiscountEngine();
        discountEngine.chooseStrategy(this.getRestaurant(), orderHistory);
        discountEngine.applyDiscount(this);
    }
}