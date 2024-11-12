package fr.unice.polytech.utility.order;

import fr.unice.polytech.utility.restaurant.RestaurantManager;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class GroupOrderProxy implements GroupOrderInterface{

    private final GroupOrderInterface groupOrderInterface;

    public GroupOrderProxy(GroupOrderInterface groupOrderInterface){
        this.groupOrderInterface = groupOrderInterface;
    }

    @Override
    public void setGroupOrderRestaurant(String restaurant) {groupOrderInterface.setGroupOrderRestaurant(restaurant);}

    @Override
    public void setGroupOrderDeliveryDate(Date deliveryDate) {groupOrderInterface.setGroupOrderDeliveryDate(deliveryDate);}

    @Override
    public void setGroupOrderDeliveryLocation(String deliveryLocation) {groupOrderInterface.setGroupOrderDeliveryLocation(deliveryLocation);}

    @Override
    public void addMember(Integer userId) {
        if (getStatus().equals("validated")) {
            throw new IllegalStateException("Impossible d'ajouter un membre car le groupe est fermé");
        }
        if (!getUserList().contains(userId)) {
            groupOrderInterface.addMember(userId);
        }
    }

    @Override
    public Order getOrder(Integer user) {
        return groupOrderInterface.getOrder(user);
    }

    @Override
    public String getRestaurant() {
        return groupOrderInterface.getRestaurant();
    }

    @Override
    public int getGroupId() {
        return groupOrderInterface.getGroupId();
    }

    @Override
    public List<Integer> getUserList() {
        return groupOrderInterface.getUserList();
    }

    @Override
    public void addOrUpdateUserOrder(Integer user, Order order) {
        if (this.getStatus().equals("validated")) {
            throw new IllegalStateException("Impossible d'ajouter ou de modifier une commande car le groupe est fermé");
        }

        if (getRestaurant().equals(order.getRestaurant()) || getGroupOrderDeliveryDate() != order.getDeliveryDate() || !getGroupOrderDeliveryLocation().equals(order.getDeliveryLocation())) {
            throw new IllegalStateException("Les paramètres de la commande ne correspondent pas à ceux du groupe");
        }

        String restaurant = groupOrderInterface.getRestaurant();
        Date groupDeliveryDate = groupOrderInterface.getGroupOrderDeliveryDate();
        int currentTotalPrepTime = groupOrderInterface.getTotalPreparationTime();

        int newTotalPrepTime = currentTotalPrepTime + order.getTotalPreparationTime(); // Minutes
        long currentTime = System.currentTimeMillis();

        if (!RestaurantManager.getRestaurantManagerInstance().findRestaurantByName(restaurant).isOpen()) {
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
    public Map<Integer, Order> getUsersOrders() {
        return groupOrderInterface.getUsersOrders();
    }

    @Override
    public void removeOrder(Integer user) {
        groupOrderInterface.removeOrder(user);
    }

    @Override
    public int getTotalPreparationTime() { return groupOrderInterface.getTotalPreparationTime();}

    @Override
    public Date getGroupOrderDeliveryDate() {
        return groupOrderInterface.getGroupOrderDeliveryDate();
    }

    @Override
    public String getGroupOrderDeliveryLocation() {
        return groupOrderInterface.getGroupOrderDeliveryLocation();
    }

    @Override
    public void validateOrder() { groupOrderInterface.validateOrder(); }

    @Override
    public String getStatus() { return groupOrderInterface.getStatus(); }

    @Override
    public void setStatus(String status) { groupOrderInterface.setStatus(status); }

    @Override
    public void closeOrder() { groupOrderInterface.closeOrder(); }

    @Override
    public Map<Integer, Integer> calculateOrderHistory() { return groupOrderInterface.calculateOrderHistory(); }

    @Override
    public void applyDiscount() { groupOrderInterface.applyDiscount(); }

}
