package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import org.mockito.internal.matchers.Or;

import java.util.HashMap;
import java.util.Map;

public class GroupOrder_2 {
    private int groupId;
    private Map<RegisteredUser, Order> usersOrders; // TODO : private Map<RegisteredUser, Order> userOrders

    public GroupOrder_2(int groupId){
        this.groupId = groupId;
        usersOrders = new HashMap<>();
    }

    public Order getOrder(RegisteredUser user){ // TODO : public getOrder(RegisteredUser user)
        return usersOrders.get(user);
    }

    public int getGroupId() {
        return groupId;
    }

    public void addOrUpdateUserOrder(RegisteredUser user, Order order){
        usersOrders.put(user, order);
    }

    public Map<RegisteredUser, Order> getUsersOrders(){
        return usersOrders;
    }

    public void removeOrder(RegisteredUser user){
        usersOrders.remove(user);
    }
}
