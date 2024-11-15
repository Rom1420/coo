package fr.unice.polytech.server.microservices;

import java.util.Date;

import fr.unice.polytech.GroupOrderManager;
import fr.unice.polytech.components.GroupOrderImpl;
import fr.unice.polytech.components.GroupOrderProxy;
import fr.unice.polytech.entities.*;

public class CreateGroup {

    private static void addGroupOrder(Integer groupOrderId, GroupOrderProxy groupOrder) {
        GroupOrderManager.getGroupOrderManagerInstance().getGroupOrders().put(groupOrderId, groupOrder);
    }

    public static void createGroup(Integer groupId, Restaurant restaurant, Date deliveryDate, String deliveryLocation) {
        GroupOrderImpl groupOrder = new GroupOrderImpl(groupId, restaurant, deliveryDate, deliveryLocation);
        GroupOrderProxy groupOrderProxy = new GroupOrderProxy(groupOrder);
        addGroupOrder(groupId, groupOrderProxy);

    }

}
