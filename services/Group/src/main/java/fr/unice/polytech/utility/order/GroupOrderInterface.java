package fr.unice.polytech.utility.order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GroupOrderInterface {
    void setGroupOrderRestaurant(String restaurant);
    void setGroupOrderDeliveryDate(Date deliveryDate);
    void setGroupOrderDeliveryLocation(String deliveryLocation);
    void addMember(Integer userId);
    Order getOrder(Integer userId);
    String getRestaurant();
    int getGroupId();
    List<Integer> getUserList();
    void addOrUpdateUserOrder(Integer userId, Order order);
    Map<Integer, Order> getUsersOrders();
    void removeOrder(Integer userId);
    int getTotalPreparationTime();
    Date getGroupOrderDeliveryDate();
    String getGroupOrderDeliveryLocation();
    void validateOrder();
    String getStatus();
    void setStatus(String status);
    void closeOrder();
    Map<Integer, Integer> calculateOrderHistory();
    void applyDiscount();
}
