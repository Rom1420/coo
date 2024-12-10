package fr.unice.polytech.components;

import fr.unice.polytech.entities.Order;
import fr.unice.polytech.entities.Restaurant;
import fr.unice.polytech.interfaces.GroupOrderInterface;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class GroupOrderProxy implements GroupOrderInterface{

    private final GroupOrderInterface groupOrderInterface;
    public GroupOrderProxy(GroupOrderInterface groupOrderInterface){
        this.groupOrderInterface = groupOrderInterface;
    }

    public GroupOrderProxy() {
        this.groupOrderInterface = new GroupOrderImpl();
    }

    @Override
    public void setGroupName(String groupName) {groupOrderInterface.setGroupName(groupName);}

    @Override
    public String getGroupName() {return groupOrderInterface.getGroupName();}

    @Override
    public void setGroupOrderRestaurant(Restaurant restaurant) {groupOrderInterface.setGroupOrderRestaurant(restaurant);}

    @Override
    public void setGroupOrderDeliveryDate(Date deliveryDate) {groupOrderInterface.setGroupOrderDeliveryDate(deliveryDate);}

    @Override
    public void setGroupOrderDeliveryLocation(String deliveryLocation) {groupOrderInterface.setGroupOrderDeliveryLocation(deliveryLocation);}

    @Override
    public void addMember(Integer user) {
        if (getStatus().equals("validated")) {
            throw new IllegalStateException("Impossible d'ajouter un membre car le groupe est fermé");
        }
        if (!getUserList().contains(user)) groupOrderInterface.addMember(user);
    }

    @Override
    public Integer getOrder(Integer id) {
        return groupOrderInterface.getOrder(id);
    }

    @Override
    public Restaurant getRestaurant() {
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
    public void addOrUpdateUserOrder(Integer id, Integer orderPreparationTime) {
        Restaurant restaurant = groupOrderInterface.getRestaurant();
        Date groupDeliveryDate = groupOrderInterface.getGroupOrderDeliveryDate();
        int currentTotalPrepTime = groupOrderInterface.getTotalPreparationTime();

        int newTotalPrepTime = currentTotalPrepTime + orderPreparationTime; // Minutes
        long currentTime = System.currentTimeMillis();

        if (getStatus().equals("validated")) {
            throw new IllegalStateException("Impossible d'ajouter ou de modifier une commande car le groupe est fermé");
        }

        /*if (getRestaurant() != order.getRestaurant() || getGroupOrderDeliveryDate() != order.getDeliveryDate() || !Objects.equals(getGroupOrderDeliveryLocation(), order.getDeliveryLocation())) {
            throw new IllegalStateException("Les paramètres de la commande ne correspondent pas à ceux du groupe");
        }*/

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
                //order.updateEstimatedDeliveryDate(estimatedTimeWithNewOrder);
                groupOrderInterface.addOrUpdateUserOrder(id, orderPreparationTime);
            }
        }

    }

    @Override
    public List<Integer> getUsersOrders() {
        return groupOrderInterface.getUsersOrders();
    }

    @Override
    public void removeOrder(Integer user) {
        groupOrderInterface.removeOrder(user);
    }

    @Override
    public int getTotalPreparationTime() {
        return groupOrderInterface.getTotalPreparationTime();
    }

    @Override
    public Date getGroupOrderDeliveryDate() {
        return groupOrderInterface.getGroupOrderDeliveryDate();
    }

    @Override
    public String getGroupOrderDeliveryLocation() {
        return groupOrderInterface.getGroupOrderDeliveryLocation();
    }

    @Override
    public void validateOrder() {
        groupOrderInterface.validateOrder();
    }

    @Override
    public String getStatus() {
        return groupOrderInterface.getStatus();
    }

    @Override
    public void setStatus(String status) {
        groupOrderInterface.setStatus(status);
    }

    @Override
    public void closeOrder() {
        groupOrderInterface.closeOrder();
    }


    @Override
    public void applyDiscount() {
        groupOrderInterface.applyDiscount();
    }
}
