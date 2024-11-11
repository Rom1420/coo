package fr.unice.polytech.utility.order;

import fr.unice.polytech.utility.restaurant.Restaurant;
import fr.unice.polytech.utility.user.RegisteredUser;
import org.mockito.internal.matchers.Or;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface GroupOrderInterface {
    Order getOrder(Integer userId);
    String getRestaurant();
    int getGroupId();
    List<Integer> getUserList();
    void addMember(Integer userId);
    void addOrUpdateUserOrder(Integer userId, Order order);
    Map<Integer, Order> getUsersOrders();
    void removeOrder(Integer userId);
    Date getGroupOrderDeliveryDate();
    String getGroupOrderDeliveryLocation();
    int getTotalPreparationTime();
}
