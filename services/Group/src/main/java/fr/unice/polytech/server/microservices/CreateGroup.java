package fr.unice.polytech.server.microservices;

import java.util.Date;

import fr.unice.polytech.db.GroupOrderManager;
import fr.unice.polytech.components.GroupOrderImpl;
import fr.unice.polytech.components.GroupOrderProxy;
import fr.unice.polytech.entities.*;

public class CreateGroup {

    private static void addGroupOrder(Integer groupOrderId, GroupOrderImpl groupOrder) {
        GroupOrderManager.getGroupOrderManagerInstance().addGroup(groupOrderId, groupOrder);
    }

    public static void createGroup(Integer groupId, Restaurant restaurant, Date deliveryDate, String deliveryLocation) {
        GroupOrderImpl groupOrder = new GroupOrderImpl(groupId, restaurant, deliveryDate, deliveryLocation);
        addGroupOrder(groupId, groupOrder);

    }

}
