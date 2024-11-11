package fr.unice.polytech.utility.order;

import fr.unice.polytech.utility.restaurant.Restaurant;
import fr.unice.polytech.utility.user.RegisteredUser;
import org.mockito.internal.matchers.Or;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface GroupOrderInterface {
    Order getOrder(RegisteredUser user);
    Restaurant getRestaurant();
    int getGroupId();
    List<RegisteredUser> getUserList();
    void addMember(RegisteredUser user);
    void addOrUpdateUserOrder(RegisteredUser user, Order order);
    Map<RegisteredUser, Order> getUsersOrders();
    void removeOrder(RegisteredUser user);
    Date getGroupOrderDeliveryDate();
    String getGroupOrderDeliveryLocation();
    int getTotalPreparationTime();
}
