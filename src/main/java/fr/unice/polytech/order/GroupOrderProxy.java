package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;

import java.util.Date;
import java.util.Map;

public class GroupOrderProxy implements GroupOrderInterface{
    private final GroupOrderInterface groupOrderInterface;
    public GroupOrderProxy(GroupOrderInterface groupOrderInterface){
        this.groupOrderInterface = groupOrderInterface;
    }
    @Override
    public Order getOrder(RegisteredUser user) {
        return groupOrderInterface.getOrder(user);
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
        Date groupDeliveryDate = groupOrderInterface.getGroupOrderDeliveryDate();
        int currentTotalPrepTime = groupOrderInterface.getTotalPreparationTime();

        int newTotalPrepTime = currentTotalPrepTime + order.getTotalPreparationTime(); // Minutes
        long currentTime = System.currentTimeMillis();

        if (!restaurant.isOpen()) {
            throw new RuntimeException("Restaurant fermé");
        }

        if (groupDeliveryDate != null) {
            long groupDeliveryTime = groupDeliveryDate.getTime(); // Milliseconds
            long estimatedTimeWithNewOrder = currentTime + (newTotalPrepTime * 60 * 1000); // Milliseconds

            if (estimatedTimeWithNewOrder > groupDeliveryTime) {
                throw new RuntimeException("Impossible d'ajouter cette commande, elle dépasserait la date de livraison du groupe.");
            }
            else{
                order.updateEstimatedDeliveryDate(estimatedTimeWithNewOrder);
                groupOrderInterface.addOrUpdateUserOrder(user, order);
            }
        }

    }

    @Override
    public Map<RegisteredUser, Order> getUsersOrders() {
        return groupOrderInterface.getUsersOrders();
    }

    @Override
    public void removeOrder(RegisteredUser user) {
        groupOrderInterface.removeOrder(user);
    }

    @Override
    public int getTotalPreparationTime() {
        return getUsersOrders().values().stream()
                .mapToInt(Order::getTotalPreparationTime)
                .sum();
    }

    @Override
    public Date getGroupOrderDeliveryDate() {
        return groupOrderInterface.getGroupOrderDeliveryDate();
    }
}
