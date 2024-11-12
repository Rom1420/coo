package fr.unice.polytech;

import fr.unice.polytech.create.CreateGroupService;
import fr.unice.polytech.utility.order.GroupOrderImpl;
import fr.unice.polytech.utility.order.GroupOrderProxy;

import java.util.HashMap;

public class GroupOrderService {

    private HashMap<Integer, GroupOrderProxy> groupOrders;

    private GroupOrderService() {
        groupOrders = new HashMap<>();
    }

    // Singleton
    private static final GroupOrderService GROUP_ORDER_SERVICE_INSTANCE = new GroupOrderService();
    public static GroupOrderService getGroupOrderServiceInstance() {
        return GROUP_ORDER_SERVICE_INSTANCE;
    }
    public HashMap<Integer, GroupOrderProxy> getGroupOrders() { return groupOrders ;}


    // int gid = new CreateGroupService().createGroup(..., ..., ..., ...); Sera effectué si la méthode de la requête est create
    // On renverra cette id au serveur appelant

}
