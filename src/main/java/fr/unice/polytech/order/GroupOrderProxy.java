package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;

import java.util.Map;

public class GroupOrderProxy implements GroupOrderInterface{
    private final GroupOrderInterface groupOrderInterface;
    public GroupOrderProxy(GroupOrderInterface groupOrderInterface){
        this.groupOrderInterface = groupOrderInterface;
    }
    @Override
    public Order getOrder(RegisteredUser user) {
        return null;
    }

    @Override
    public int getGroupId() {
        return groupOrderInterface.getGroupId();
    }

    @Override
    public Restaurant getRestaurant() {
        return groupOrderInterface.getRestaurant();
    }

    @Override
    public void addOrUpdateUserOrder(RegisteredUser user, Order order) {
        Restaurant restaurant = groupOrderInterface.getRestaurant();
        if (restaurant.isOpen() /*&& restaurant.canPrepare(order, groupOrderInterface.getUsersOrders())*/) {
            groupOrderInterface.addOrUpdateUserOrder(user, order);
        } else {
            throw new RuntimeException("Restaurant non disponible pour cette commande.");
        }
    }

    @Override
    public Map<RegisteredUser, Order> getUsersOrders() {
        return null;
    }

    @Override
    public void removeOrder(RegisteredUser user) {

    }
}
