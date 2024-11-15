package fr.unice.polytech.interfaces;

import fr.unice.polytech.entities.Order;
import fr.unice.polytech.entities.Restaurant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GroupOrderInterface {
    void setGroupOrderRestaurant(Restaurant restaurant);
    void setGroupOrderDeliveryDate(Date deliveryDate);
    void setGroupOrderDeliveryLocation(String deliveryLocation);
    void addMember(Integer user);
    Order getOrder(Integer user);
    Restaurant getRestaurant();
    int getGroupId();
    List<Integer> getUserList();
    void addOrUpdateUserOrder(Integer user, Order order);
    Map<Integer, Order> getUsersOrders();
    void removeOrder(Integer user);
    int getTotalPreparationTime();
    Date getGroupOrderDeliveryDate();
    String getGroupOrderDeliveryLocation();
    void validateOrder();
    String getStatus();
    void setStatus(String status);
    void closeOrder();
    void applyDiscount();
}
