package fr.unice.polytech;


import fr.unice.polytech.components.GroupOrderProxy;


import java.util.HashMap;


public class GroupOrderManager {


    private static int groupOrderId = 0;
    private HashMap<Integer, GroupOrderProxy> groupOrders;

    private GroupOrderManager() {
        groupOrders = new HashMap<>();
    }

    // Singleton
    private static final GroupOrderManager GROUP_ORDER_MANAGER_INSTANCE = new GroupOrderManager();

    public static GroupOrderManager getGroupOrderManagerInstance() {
        return GROUP_ORDER_MANAGER_INSTANCE;
    }

    public HashMap<Integer, GroupOrderProxy> getGroupOrders() { return groupOrders; }

    public Integer  getGroupOrderId() { return groupOrderId; }
    public Integer getGroupOrderIdAndIncrease() {
        groupOrderId++;
        return groupOrderId;
    }


}

