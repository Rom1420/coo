package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import org.mockito.internal.matchers.Or;

import java.util.HashMap;
import java.util.Map;


public interface GroupOrderInterface {
    Order getOrder(RegisteredUser user);
    int getGroupId();
    void addOrUpdateUserOrder(RegisteredUser user, Order order);
    Map<RegisteredUser, Order> getUsersOrders();
    void removeOrder(RegisteredUser user);
}
