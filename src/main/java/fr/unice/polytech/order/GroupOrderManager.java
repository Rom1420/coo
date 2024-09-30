package fr.unice.polytech.order;

import java.util.HashMap;

public class GroupOrderManager {

    private HashMap<Integer, GroupOrder> groupOrders;

    private GroupOrderManager() {
        groupOrders = new HashMap<>();
    }

    // Singleton
    private static final GroupOrderManager GROUP_ORDER_MANAGER_INSTANCE = new GroupOrderManager();

    public static GroupOrderManager getGroupOrderManagerInstance() {
        return GROUP_ORDER_MANAGER_INSTANCE;
    }

    public void addGroupOrder(Integer groupOrderId, GroupOrder groupOrder) {
        groupOrders.put(groupOrderId, groupOrder);
    }

    public GroupOrder getGroupOrder(Integer groupOrderId) {
        return groupOrders.get(groupOrderId);
    }

    public void removeOrder(Integer groupOrderId) {
        groupOrders.remove(groupOrderId);
    }
}

