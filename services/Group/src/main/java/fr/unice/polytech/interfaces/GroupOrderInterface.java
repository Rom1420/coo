package fr.unice.polytech.interfaces;

import fr.unice.polytech.entities.Order;
import fr.unice.polytech.entities.Restaurant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GroupOrderInterface {
    void setGroupName(String groupName);
    String getGroupName();
    void setGroupOrderRestaurant(Restaurant restaurant);
    void setGroupOrderDeliveryDate(Date deliveryDate);
    void setGroupOrderDeliveryLocation(String deliveryLocation);
    void addMember(Integer user);
    Integer getOrder(Integer id);
    Restaurant getRestaurant();
    int getGroupId();
    List<Integer> getUserList();
    void addOrUpdateUserOrder(Integer id, Integer orderPreparationTime);
    List<Integer> getUsersOrders();
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
