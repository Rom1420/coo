package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUserManager;

import java.util.Date;
import java.util.HashMap;

import static fr.unice.polytech.user.RegisteredUserManager.getRegisteredUserManagerInstance;

public class GroupOrderManager {

    private static int groupOrderId = 0;

    private HashMap<Integer, GroupOrderImpl> groupOrders;

    private GroupOrderManager() {
        groupOrders = new HashMap<>();
    }

    // Singleton
    private static final GroupOrderManager GROUP_ORDER_MANAGER_INSTANCE = new GroupOrderManager();

    public static GroupOrderManager getGroupOrderManagerInstance() {
        return GROUP_ORDER_MANAGER_INSTANCE;
    }
    public Integer  getGroupOrderId() { return groupOrderId; }


    public Integer addGroupOrder() {
        int currentGroupId = groupOrderId;
        groupOrders.put(currentGroupId, new GroupOrderImpl(currentGroupId));
        groupOrderId++;
        return currentGroupId;
    }

    public void setGroupOrderAttributes(Integer groupOrderId, Restaurant restaurant, String deliveryLocation, Date deliveryDate) {
        GroupOrderImpl groupOrder = groupOrders.get(groupOrderId);
        groupOrder.setGroupOrderRestaurant(restaurant);
        groupOrder.setGroupOrderDeliveryLocation(deliveryLocation);
        groupOrder.setGroupOrderDeliveryDate(deliveryDate);
    }

    public void createGroup(Boolean validate, Integer creatorId, Restaurant restaurant, String deliveryLocation, Date deliveryDate, Order order) {
        if (validate) { // On valide la création d'un groupe avec les inputs
            int gid = addGroupOrder();
            setGroupOrderAttributes(gid, restaurant, deliveryLocation, deliveryDate);
            RegisteredUserManager registeredUsers = getRegisteredUserManagerInstance();
            getGroupOrderById(gid).addMember(registeredUsers.getRegisteredUserById(creatorId));
            getGroupOrderById(gid).addOrUpdateUserOrder(registeredUsers.getRegisteredUserById(creatorId), order);
        }
    }

    public GroupOrderImpl getGroupOrderById(Integer groupOrderId) {
        return groupOrders.get(groupOrderId);
    }

    public HashMap<Integer, GroupOrderImpl> getGroupOrders() {
        return groupOrders;
    }

    public void removeGroupOrderById(Integer groupOrderId) {
        groupOrders.remove(groupOrderId);
    }
}

