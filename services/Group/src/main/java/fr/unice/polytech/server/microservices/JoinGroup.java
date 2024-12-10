package fr.unice.polytech.server.microservices;

import fr.unice.polytech.components.GroupOrderImpl;
import fr.unice.polytech.db.GroupOrderManager;

import java.util.List;

public class JoinGroup {

    public static void addOrderToGroup(Integer groupId, Integer orderId, Integer preparationTime) {
        GroupOrderManager.getGroupOrderManagerInstance().addOrderToExistingGroup(groupId, orderId, preparationTime);
    }


    public static List<Integer> getGroupOrderIds(Integer groupId) {
        GroupOrderImpl groupOrder = getGroupOrder(groupId);
        if (groupOrder == null) {
            throw new IllegalArgumentException("Group ID not found");
        }
        return groupOrder.getUsersOrders();
    }

    private static GroupOrderImpl getGroupOrder(Integer groupId) {
        return (GroupOrderImpl) GroupOrderManager.getGroupOrderManagerInstance()
                .getRawGroupOrders()
                .get(groupId);
    }
}
