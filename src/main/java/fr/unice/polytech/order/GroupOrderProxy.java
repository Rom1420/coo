package fr.unice.polytech.order;

import fr.unice.polytech.user.RegisteredUser;

import java.util.Map;

public class GroupOrderProxy implements GroupOrderInterface{
    @Override
    public Order getOrder(RegisteredUser user) {
        return null;
    }

    @Override
    public int getGroupId() {
        return 0;
    }

    @Override
    public void addOrUpdateUserOrder(RegisteredUser user, Order order) {

    }

    @Override
    public Map<RegisteredUser, Order> getUsersOrders() {
        return null;
    }

    @Override
    public void removeOrder(RegisteredUser user) {

    }
}
