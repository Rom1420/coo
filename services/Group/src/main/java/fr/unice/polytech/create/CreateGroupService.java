package fr.unice.polytech.create;

import fr.unice.polytech.GroupOrderService;
import fr.unice.polytech.utility.order.GroupOrderImpl;
import fr.unice.polytech.utility.order.GroupOrderProxy;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;


public class CreateGroupService {

    private static int groupOrderId = 0;
    HashMap<Integer, GroupOrderProxy> groupOrders = GroupOrderService.getGroupOrderServiceInstance().getGroupOrders();

    public Integer addGroupOrder() {
        int currentGroupId = groupOrderId;
        groupOrders.put(currentGroupId, new GroupOrderProxy(new GroupOrderImpl(currentGroupId)));
        groupOrderId++;
        return currentGroupId;
    }

    public void setGroupOrderAttributes(Integer groupOrderId, String restaurantName, String deliveryLocation, String deliveryDate) {
        GroupOrderProxy groupOrder = groupOrders.get(groupOrderId);
        groupOrder.setGroupOrderRestaurant(restaurantName);
        groupOrder.setGroupOrderDeliveryLocation(deliveryLocation);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(deliveryDate);
        Date date = Date.from(zonedDateTime.toInstant());
        groupOrder.setGroupOrderDeliveryDate(date);
    }

    public Integer createGroup(Boolean validate, String restaurantName, String deliveryLocation, String deliveryDate) {
        if (validate) { // On valide la création d'un groupe avec les inputs
            int gid = addGroupOrder();
            setGroupOrderAttributes(gid, restaurantName, deliveryLocation, deliveryDate);
            return gid; // Valeur correspondante à l'id du groupe crée
        }
        return -1; // Valeur signalant une erreur
    }
}
